package com.example.libraryapi.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {
    private TokenSettings tokenSettings;
    private String secretKey;
    private List<OpenedRoute> whiteList;
    @Setter
    @Getter
    @AllArgsConstructor
    public static class OpenedRoute {
        private String url;
        private String methods;
    }
    @Setter
    public static class TokenSettings {
        private Integer accessTokenLifetimeInMinutes;

    }
    public Integer getAccessTokenLifetimeInMinutes() {
        return tokenSettings.accessTokenLifetimeInMinutes;
    }

}