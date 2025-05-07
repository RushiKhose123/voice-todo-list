package com.example.demo.config;

import com.example.demo.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.ClientInfoStatus;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String API = "/api/**";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String OPTIONS = "OPTIONS";



    @Value("${allowed.origin}")
    private String origin;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        String [] allowedOrigins = origin.split(CommonConstants.COMMA);
        List<String> allowedOriginsParsed ;
        allowedOriginsParsed = Arrays.stream(allowedOrigins).map(String::trim).collect(Collectors.toList());
        registry.addMapping(API)
                .allowedOrigins(allowedOriginsParsed.get(0),allowedOriginsParsed.get(1))
                .allowedMethods(GET, POST, PUT, DELETE, OPTIONS)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
