package com.lingosphinx.gamification.scheduling;

import com.lingosphinx.gamification.service.HabitReminderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HabitReminderScheduler {

    private final HabitReminderService habitReminderService;

    @Scheduled(cron = "0 0 9 * * *")
    public void morningReminder() {
        log.info("Sending Streak reminder (morning)");
        habitReminderService.remindAll();
    }

    @Scheduled(cron = "0 0 18 * * *")
    public void eveningReminder() {
        log.info("Sending Streak reminder (evening)");
        habitReminderService.remindAll();
    }
}