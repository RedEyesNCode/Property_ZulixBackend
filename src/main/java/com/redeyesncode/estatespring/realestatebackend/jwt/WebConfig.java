package com.redeyesncode.estatespring.realestatebackend.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedOrigins("*");
////        registry.addMapping("/**")
////                .allowedOrigins("https://lb-pearl.vercel.app/")
////                .allowedOrigins("https://lb-pearl.vercel.app")
////                .allowedOrigins("http://localhost:3001/")
////                .allowedOrigins("http://localhost:3001")
////                .allowedOrigins("http://localhost:3001/spring-property/")
////
////                // Allow requests from any origin
////                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
////                .allowedHeaders("*") // Allow all headers
////                .allowCredentials(true); // Allow including cookies
//    }

}
