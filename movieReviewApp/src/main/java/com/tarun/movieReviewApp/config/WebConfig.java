package com.tarun.movieReviewApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3002")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowCredentials(true);
      WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
