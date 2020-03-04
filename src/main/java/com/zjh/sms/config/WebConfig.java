package com.zjh.sms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description 解决vue+spring boot跨域问题
 * Author: zjh
 * Date: 2020/2/26 22:48
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
          .allowedOrigins("*")
          .allowCredentials(true)
          .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
          .maxAge(3600 * 24);
    }
}
