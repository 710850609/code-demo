package me.libo.web.auth.jwt.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LinBo
 * @date 2019/11/10 13:44
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private String loginUri = "/login";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (loginUri.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getBearerParam(request);
        if (token == null || "".equals(token.trim())) {
            throw new RuntimeException("Token为空");
        }
        Jwt<Header, Claims> jwt = Jwts.parser().parse(token);

        String subject = jwt.getBody().getSubject();
        log.info("[{}] 请求...", subject);
        filterChain.doFilter(request, response);
    }

    private String getBearerParam(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            return null;
        }
        int bearer_ = authorization.indexOf("Bearer ");
        if (bearer_ < 0) {
            return null;
        }
        return authorization.substring(7);
    }

}
