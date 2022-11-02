package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.interceptor.SessionInterceptor;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
    @Bean
    public SessionInterceptor getSessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionInterceptor()).
                addPathPatterns(
                    "/**"
                ).excludePathPatterns(
                        "/login",
                        "/buy/**",
                        "/setMsg",
                        "/getMsg",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.ico",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.woff",
                        "/**/*.ttf");
    }
}
