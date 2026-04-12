package com.econauts.UIConnect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")

                        // Allow multiple environments
                        .allowedOriginPatterns(
                                "http://localhost:3000", 
                                "http://192.168.*.*:3000", // LAN (your multi-PC setup)
                                "https://yourdomain.com"   // production (change later)
                        )

                        // Allow all HTTP methods
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // Allow headers
                        
                        
                        .allowedHeaders("*")

                        // Allow cookies / auth headers
                        .allowCredentials(true)

                        // Cache preflight response (performance)
                        .maxAge(3600);
            }
        };
    }
}