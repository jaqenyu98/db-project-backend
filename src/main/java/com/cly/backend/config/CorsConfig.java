package com.cly.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //have to be originPatterns, allowOrigins is useless!
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                //must assign this! allow authorization in headers.
                .allowedHeaders("*")
                .exposedHeaders("*")
                .maxAge(86400L);
    }
}
