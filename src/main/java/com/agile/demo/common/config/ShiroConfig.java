package com.agile.demo.common.config;

import com.agile.demo.common.authentication.OAuthFilter;
import com.agile.demo.common.authentication.OAuthRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/5/15
 */

@Configuration
public class ShiroConfig {

    /**
     * 权限过滤器
     * @param securityManager
     * @return
     */
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/sys/unauthorized");
        shiroFilter.setUnauthorizedUrl("/sys/unauthorized");
        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth", new OAuthFilter());
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/actuator/**", "anon");
        //APP 模块开放 后面通过拦截器管理
        filterMap.put("/app/**", "anon");
        //用户密码登录
        filterMap.put("/sys/login", "anon");
        //未认证
        filterMap.put("/sys/unauthorized", "anon");
        //验证码
        filterMap.put("/sys/captcha/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/**", "oauth");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * 配置安全事务管理器
     * @param authRealm
     * @param sessionManager
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(OAuthRealm authRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        manager.setSessionManager(sessionManager);
        return manager;
    }

    /**
     * session管理
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }



    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
