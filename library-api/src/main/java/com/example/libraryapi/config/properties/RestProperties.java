package com.example.libraryapi.config.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Component
@Validated
@ConfigurationProperties(prefix = "app.api.rest")
public class RestProperties {
    @NotNull
    private LibraryTracker libraryTracker;
    private Integer connectionTimeoutInMs;

    public record LibraryTracker(String url) {}

    public String libraryTrackerUrl(){
        return libraryTracker.url;
    }
}
