package com.lingosphinx.gamification.scheduling;

import com.lingosphinx.gamification.service.SchedulingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HabitReminderScheduler {

    private final SchedulingService schedulingService;

    @Scheduled(cron = "0 0 */1 * * *")
    public void morningReminder() {
        log.info("Sending Streak reminders");
        schedulingService.remindAll();
    }
}