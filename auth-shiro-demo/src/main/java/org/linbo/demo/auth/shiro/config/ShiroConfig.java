package org.linbo.demo.auth.shiro.config;

import org.apache.shiro.mgt.RememberMeManager;

import org.apache.catalina.SessionListener;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.session.DefaultWebSessionManager;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {

    // shiro 过滤拦截规则
    private static final String SHIRO_FILTER ;

    static {
        // 无需认证接口
        String[] annonList = {
                "/swagger**", "/swagger-resources/**", "/configuration/**", "/v2/api-docs", "/webjars/**", // swagger-ui
                // 系统基础接口（无需鉴权即可访问）
        };
        // 需要登录认证接口
        String[] authcList = {
                "/manage/users/update", "/manage/menus/permissionTree" // 用户基础接口
        };
        StringBuilder filterBuf = new StringBuilder();
        for (String str : annonList) {
            filterBuf.append(str).append("=anon;");
        }
        for (String str : authcList) {
            filterBuf.append(str).append("=authc;");
        }
        filterBuf.append("/**=roles[admin];"); // admin角色用于所有接口权限
        filterBuf.append("/**=perms;"); // 其他用户进行权限拦截
        SHIRO_FILTER = filterBuf.toString();
    }

    @Bean
    public SessionDAO sessionDao(IRealm realm) {
        RedisSessionDAO dao = new RedisSessionDAO();
        realm.setSessionDAO(dao);
        return dao;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(AuthorizingRealm realm, SessionManager sessionManager,
                                                     RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setCacheManager(new RedisCacheManager());
        manager.setSessionManager(sessionManager);
        manager.setRememberMeManager(rememberMeManager);
        return manager;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDao, SessionListener sessionListener, Cookie cookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.getSessionListeners().add(sessionListener);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    @Bean
    public Cookie cookie() {
        SimpleCookie cookie = new SimpleCookie("IBASE4JSESSIONID");
        cookie.setSecure(PropertiesUtil.getBoolean("session.cookie.secure", false));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        return cookie;
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.getCookie().setMaxAge(PropertiesUtil.getInt("rememberMe.cookie.maxAge", 60 * 60 * 24));
        return rememberMeManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setSecurityManager(securityManager);
        factory.setLoginUrl("/unauthorized");
        factory.setUnauthorizedUrl("/forbidden");
        Map<String, Filter> fm = new HashMap<>();
        fm.put("perms", new HttpPermissionsAuthorizationFilter());
        fm.put("authc", new HttpFormAuthenticationFilter ());
        factory.setFilters(fm);

        Map<String, String> filterMap = InstanceUtil.newLinkedHashMap();
        for (String filter : SHIRO_FILTER.split("\\;")) {
            String[] keyValue = filter.split("\\=");
            filterMap.put(keyValue[0], keyValue[1]);
        }
        factory.setFilterChainDefinitionMap(filterMap);
        return factory;
    }

}
