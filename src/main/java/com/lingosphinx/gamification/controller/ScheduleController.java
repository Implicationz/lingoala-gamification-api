package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.service.HabitReminderService;
import com.lingosphinx.gamification.service.HabitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Tag(name = "Schedule API")
public class ScheduleController {

    private final HabitService habitService;
    private final HabitReminderService habitReminderService;

    @Value("${cron.secret}")
    private String cronSecret;

    @PostMapping("/habit/reset")
    public ResponseEntity<HabitDto> resetAll(@RequestHeader("X-CRON-SECRET") String secret) {
        if (!cronSecret.equals(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        habitService.resetAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/habit/remind")
    public ResponseEntity<HabitDto> remindAll(@RequestHeader("X-CRON-SECRET") String secret) {
        if (!cronSecret.equals(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        habitReminderService.remindAll();
        return ResponseEntity.ok().build();
    }
}