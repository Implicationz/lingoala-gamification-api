package com.lingosphinx.gamification.client;

import com.lingosphinx.gamification.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${notification.client.url}")
public interface NotificationClient {
    @PostMapping("/notification")
    void create(@RequestBody NotificationDto notificationDto);
}