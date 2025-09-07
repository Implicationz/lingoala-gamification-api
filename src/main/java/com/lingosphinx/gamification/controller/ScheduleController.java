package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.service.HabitService;
import com.lingosphinx.gamification.service.SchedulingService;
import com.lingosphinx.gamification.service.ScoreSnapshotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Tag(name = "Schedule API")
public class ScheduleController {

    private final SchedulingService schedulingService;

    private final HabitService habitService;
    private final ScoreSnapshotService scoreSnapshotService;

    @Value("${cron.secret}")
    private String cronSecret;

    @PostMapping("/habit/reset/{region}/{city}")
    public ResponseEntity<Void> resetAll(
            @RequestHeader("X-CRON-SECRET") String secret,
            @PathVariable String region,
            @PathVariable String city) {
        if (!cronSecret.equals(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        habitService.resetAll(IanaTimeZone.of(region, city));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/habit/remind")
    public ResponseEntity<Void> remindAll(@RequestHeader("X-CRON-SECRET") String secret) {
        if (!cronSecret.equals(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        schedulingService.remindAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/snapshot/regenerate/{region}/{city}")
    public ResponseEntity<Void> regenerateAll(@RequestHeader("X-CRON-SECRET") String secret,
                                              @PathVariable String region,
                                              @PathVariable String city) {
        if (!cronSecret.equals(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        scoreSnapshotService.regenerateAll(IanaTimeZone.of(region, city));
        return ResponseEntity.ok().build();
    }
}