package com.houseant.backend.config;

import com.houseant.backend.interceptor.AutoLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AutoLoginInterceptor autoLoginInterceptor;

    @Autowired
    public WebConfig(AutoLoginInterceptor autoLoginInterceptor) {
        this.autoLoginInterceptor = autoLoginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoLoginInterceptor).addPathPatterns("/**");
    }

}
