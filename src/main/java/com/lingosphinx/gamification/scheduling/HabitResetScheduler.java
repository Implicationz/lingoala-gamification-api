package com.lingosphinx.gamification.scheduling;

import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.service.HabitService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HabitResetScheduler {

    private final HabitService habitService;
    private final TaskScheduler taskScheduler;
    private final List<IanaTimeZone> supportedTimeZones = List.of(
            IanaTimeZone.of("Europe/Berlin"),
            IanaTimeZone.of("America/New_York")
    );

    @PostConstruct
    public void scheduleMidnightTasks() {
        for (var tz : supportedTimeZones) {
            scheduleForTimeZone(tz);
        }
    }

    private void scheduleForTimeZone(IanaTimeZone tz) {
        var now = ZonedDateTime.now(tz.zoneId());
        var nextMidnight = now.with(LocalTime.MIDNIGHT).plusDays(now.toLocalTime().isAfter(LocalTime.MIDNIGHT) ? 1 : 0);

        taskScheduler.scheduleAtFixedRate(() -> {
            log.info("Midnight in {}: executing reset", tz.zoneId());
            try {
                habitService.resetAll(tz);
            }
            catch (Throwable t) {
                log.error("Error during habit reset for timezone {}", tz.zoneId(), t);
            }
        }, nextMidnight.toInstant(), Duration.ofDays(1));
    }
}