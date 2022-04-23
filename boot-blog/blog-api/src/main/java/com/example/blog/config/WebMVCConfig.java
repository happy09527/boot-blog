package com.example.blog.config;

import com.example.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hap HWADDR
 * @date 2022/4/16 17:24
 * @describe webMvc配置类
 */

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * 允许跨域配置，前后端分离
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("http://localhost:8080")
//                .allowedOriginPatterns("https://haper.top")
//                .allowedOriginPatterns("https://180.76.149.47")
                .allowedOriginPatterns("*");
    }

    /**
     * 增加登录拦截的接口
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //假设拦截test接口后续实际遇到拦截的接口是时，再配置真正的拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
//                .addPathPatterns("/upload/avatar");
    }
}