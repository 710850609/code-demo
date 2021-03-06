package me.libo.web.auth.jwt.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author LinBo
 * @date 2019/11/10 13:44
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_PREFIX = "Bearer ";
    /**
     * 认证如果失败由该端点进行响应
     */
    private AuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
    private JwtTokenGenerator jwtTokenGenerator;
    private JwtTokenStorage jwtTokenStorage;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果已经通过认证
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilterApplicationContext (request, response);
            return;
        }
        // 获取 header 解析出 jwt 并进行认证 无token 直接进入下一个过滤器  因为  SecurityContext 的缘故 如果无权限并不会放行
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(AUTHENTICATION_PREFIX)) {
            String jwtToken = header.replace(AUTHENTICATION_PREFIX, "");


            if (StringUtils.hasText(jwtToken)) {
                try {
                    authenticationTokenHandle(jwtToken, request);
                } catch (AuthenticationException e) {
                    authenticationEntryPoint.commence(request, response, e);
                }
            } else {
                // 带安全头 没有带token
                authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("token is not found"));
            }

        }
        filterChain.doFilter(request, response);
    }


    /**
     * 具体的认证方法  匿名访问不要携带token
     * 有些逻辑自己补充 这里只做基本功能的实现
     *
     * @param jwtToken jwt token
     * @param request  request
     */
    private void authenticationTokenHandle(String jwtToken, HttpServletRequest request) throws AuthenticationException {

        // 根据我的实现 有效token才会被解析出来
        JSONObject jsonObject = jwtTokenGenerator.decodeAndVerify(jwtToken);

        if (Objects.nonNull(jsonObject)) {
            String username = jsonObject.getStr("aud");

            // 从缓存获取 token
            JwtTokenPair jwtTokenPair = jwtTokenStorage.get(username);
            if (Objects.isNull(jwtTokenPair)) {
                if (log.isDebugEnabled()) {
                    log.debug("token : {}  is  not in cache", jwtToken);
                }
                // 缓存中不存在就算 失败了
                throw new CredentialsExpiredException("token is not in cache");
            }
            String accessToken = jwtTokenPair.getAccessToken();

            if (jwtToken.equals(accessToken)) {
                // 解析 权限集合  这里
                JSONArray jsonArray = jsonObject.getJSONArray("roles");

                String roles = jsonArray.toString();

                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
                User user = new User(username, "[PROTECTED]", authorities);
                // 构建用户认证token
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 放入安全上下文中
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                // token 不匹配
                if (log.isDebugEnabled()){
                    log.debug("token : {}  is  not in matched", jwtToken);
                }

                throw new BadCredentialsException("token is not matched");
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("token : {}  is  invalid", jwtToken);
            }
            throw new BadCredentialsException("token is invalid");
        }
    }
}
