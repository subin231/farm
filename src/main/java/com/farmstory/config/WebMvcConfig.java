package com.farmstory.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${spring.servlet.multipart.location}")
    private String resourcepath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // URL에서 /uploads/**로 접근할 때, 실제 파일 시스템의 경로를 참조하도록 설정
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:"+resourcepath);
    }
}
