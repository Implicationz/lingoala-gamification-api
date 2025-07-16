package com.lingosphinx.gamification.scheduling;

import com.lingosphinx.notification.service.NotificationJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationJobScheduler {

    private final NotificationJobService notificationJobService;

    @Scheduled(cron = "*/30 * * * * *")
    public void scheduleWorkers() {
        notificationJobService.processAll();
    }
}