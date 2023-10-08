package com.example.libraryapi.config;


import com.example.libraryapi.config.properties.RestProperties;
import com.example.libraryapi.config.properties.SecurityProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Bean
    public Map<PathPattern, List<String>> pathPatterns(SecurityProperties properties, PathPatternParser parser) {
        return properties.getWhiteList()
                .stream()
                .collect(
                        Collectors.toMap(
                                openedRoute -> parser.parse(openedRoute.getUrl()),
                                openedRoute -> Arrays.stream(openedRoute.getMethods().split(","))
                                        .map(String::trim)
                                        .toList()
                        )
                );
    }

    @Bean
    public RestTemplate restTemplate(RestProperties properties) {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(properties.getConnectionTimeoutInMs()))
                .build();
    }

    @Bean
    public PathPatternParser parser() {
        return new PathPatternParser();
    }

}
