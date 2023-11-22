package com.oneteam.dormeaseadmin.config;

import com.oneteam.dormeaseadmin.admin.member.MemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/dormEaseUploadImg/**")
                .addResourceLocations("file:///c://dormEase/upload/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new MemberInterceptor())
                .addPathPatterns(
                        "/admin/member/modify_form",
                        "/product/**",
                        "/board/"
                        );
    }

}
