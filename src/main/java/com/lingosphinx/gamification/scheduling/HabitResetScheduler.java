package com.lingosphinx.gamification.scheduling;

import com.lingosphinx.gamification.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HabitResetScheduler {

    private final HabitService habitService;

    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyStreaks() {
        log.info("Starting daily Streak reset");
        habitService.resetAll();
    }
}