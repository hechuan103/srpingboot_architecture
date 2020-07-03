package com.hc.architecture.webconf;

import com.hc.architecture.config.common.i18n.LanguageLocalResolver;
import com.hc.architecture.config.common.util.EasiRequestHeaderUtil;
import com.hc.architecture.webconf.intercept.EasiLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @Author: hechuan
 * @Date: 2020/5/18 17:45
 * @Description: spring mvc 添加拦截器配置
 */
@Configuration
public class EasiWebMvcConfig implements WebMvcConfigurer {


    private String[] ignoreSwaggerUrl = {"/**/doc.html","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"};

    @Bean("localeResolver")
    public LocaleResolver getResolver() {
        return new LanguageLocalResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(EasiRequestHeaderUtil.USER_LANGUAGE);
        return lci;
    }

    @Bean
    public EasiLoginInterceptor easiLoginInterceptor() {
        EasiLoginInterceptor easilogin = new EasiLoginInterceptor();

        return easilogin;
    }

    /**
     * 资源文件配置
     * @param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //html
//        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        // js
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 添加拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(easiLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/**/login/**")
                //swagger
                .excludePathPatterns(ignoreSwaggerUrl);
    }

    /**
     * 新增formatter 转换器或者格式化工具
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

        //registry.addConverter(new EasiDateConverter());
    }
}
