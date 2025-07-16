package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.service.HabitReminderService;
import com.lingosphinx.gamification.service.HabitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Tag(name = "Schedule API")
public class ScheduleController {

    private final HabitService habitService;
    private final HabitReminderService habitReminderService;

    @PostMapping("/habit/reset")
    public ResponseEntity<HabitDto> resetAll() {
        habitService.resetAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/habit/remind")
    public ResponseEntity<HabitDto> remindAll() {
        habitReminderService.remindAll();
        return ResponseEntity.ok().build();
    }
}