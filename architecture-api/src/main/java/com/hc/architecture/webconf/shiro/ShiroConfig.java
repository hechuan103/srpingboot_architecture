package com.hc.architecture.webconf.shiro;

import com.hc.architecture.biz.permission.shiro.ShiroRealm;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Getter
@Setter
public class ShiroConfig {

    private String host;

    private int port;


    // 1、创建realm
    @Bean
    public ShiroRealm getRealm() {
        return new ShiroRealm();
    }

    //2、配置安全管理器
    @Bean
    public SecurityManager getSecurityManager(ShiroRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        //配置会话管理器
        securityManager.setSessionManager(getSessionManager());
        //配置缓存管理器
//        securityManager.setCacheManager(getRedisCacheManager());
        return securityManager;
    }

    // 3、配置shiro的过滤器工厂
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        //1、创建过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //2、配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //3、通用配置
        //如果没有登录默认会跳到一个login.jsp,这里我们需要返回未登录json
        shiroFilterFactoryBean.setLoginUrl("/login/unLogin");

        // 4、过滤器集合
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainMap.put("/static/**", "anon");
        filterChainMap.put("/**/login/**", "anon");
        //knife4j 静态文件
        filterChainMap.put("/webjars/**", "anon");
        filterChainMap.put("/doc.html", "anon");
        filterChainMap.put("/swagger-resources/**", "anon");
        filterChainMap.put("/v2/**", "anon");
        filterChainMap.put("/swagger-ui.html/**", "anon");
        // 所有的一些请求都需要登录后访问
        filterChainMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    //4、shiro session manager会话管理没有配置默认使用ServletContainerSessionManager
    // 4.1 redisManager配置
/*    public RedisManager getRedisManger() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host + ":" + port);
        return redisManager;
    }

    //4.2 配置sessionDao
    public RedisSessionDAO getRedisSessionDao() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManger());
        return redisSessionDAO;
    } */

    // 4.3 会话管理器
    public DefaultWebSessionManager getSessionManager() {
        EasiSessionManager sessionManager = new EasiSessionManager();
        // 设置session过期时间使用毫秒做单位，默认值 30分钟。
//        sessionManager.setGlobalSessionTimeout(30*1000L);
//        sessionManager.setSessionDAO(getRedisSessionDao());
        return sessionManager;
    }
    /*
    // 4.4 配置缓存管理器
    public RedisCacheManager getRedisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setKeyPrefix("session");
        redisCacheManager.setRedisManager(getRedisManger());
        return redisCacheManager;
    }*/


    //5、启用注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     * @return
     * DefaultAdvisorAutoProxyCreator的顺序必须在shiroFilterFactoryBean之前，不然SecurityUtils.getSubject().getPrincipal()获取不到参数
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        advisorAutoProxyCreator.setUsePrefix(true);
        return advisorAutoProxyCreator;
    }



}
