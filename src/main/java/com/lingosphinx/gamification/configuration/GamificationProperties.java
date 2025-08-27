package com.lingosphinx.gamification.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "lingosphinx.gamification")
public class GamificationProperties {
    private String apiKey;
}