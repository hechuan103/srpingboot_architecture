package com.hc.architecture.config.restTemplet;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: hechuan
 * @Date: 2020/5/21 19:39
 * @Description: 配置http请求
 */
@Configuration
@ConfigurationProperties(prefix = "httpclient.config")
@Getter
@Setter
public class RestTemplateConfig {


    private int readTimeout; //获取数据超时
    private int connectTimeout; //建立连接超时

    //private int requestConnectTimeout; //从连接池中获取连接的超时时间



    /**
     * 设置restTemplate为HttpClient
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }
}
