package com.ivyxo.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器 Richard - 2019-12-3 22:07:10
 * @author HYR
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //需要拦截的路径
        String[] addPathPatterns = {
                "/v1/user/**"
        };

        //不需要拦截的路径
        String[] excludePathPatterns = {

        };

        //注册登录拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPatterns);
    }
}
