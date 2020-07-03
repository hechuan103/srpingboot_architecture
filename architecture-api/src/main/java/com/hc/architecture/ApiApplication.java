package com.hc.architecture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: hechuan
 * @Date: 2020/5/14 09:43
 * @Description: 工程启动类
 */
@SpringBootApplication
@MapperScan("com.hc.architecture.biz.**.mapper")
public class ApiApplication {

    public static void main(String[] args) {
       SpringApplication app = new SpringApplication(ApiApplication.class);
       //关闭spring的banner
       app.setBannerMode(Banner.Mode.OFF);
       app.run(args);
    }
}
