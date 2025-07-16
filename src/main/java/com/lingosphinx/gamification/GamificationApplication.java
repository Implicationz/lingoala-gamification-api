package com.lingosphinx.gamification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.lingosphinx.gamification", "com.lingosphinx.notification"})
@EnableJpaRepositories(basePackages = {
        "com.lingosphinx.gamification.repository",
        "com.lingosphinx.notification.repository"
})
@EntityScan(basePackages = {
        "com.lingosphinx.gamification.domain",
        "com.lingosphinx.notification.domain"
})
@EnableCaching
@EnableAsync
@EnableScheduling
public class GamificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamificationApplication.class, args);
    }

}
