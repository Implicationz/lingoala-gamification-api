package com.lingosphinx.gamification.scheduling;

import com.lingosphinx.gamification.service.ScoreSnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreSnapshotRegenerateScheduler {

    private final ScoreSnapshotService scoreSnapshotService;

    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyStreaks() {
        log.info("Starting daily snapshot regeneration");
        scoreSnapshotService.regenerateAll();
    }
}