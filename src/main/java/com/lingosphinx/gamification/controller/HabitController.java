package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.HabitActivationDto;
import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.service.HabitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
@Tag(name = "Habit API")
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/activation")
    public ResponseEntity<HabitDto> activation(@RequestBody @Valid HabitActivationDto habitActivation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitService.activate(habitActivation));
    }

    @PostMapping
    public ResponseEntity<HabitDto> create(@RequestBody @Valid HabitDto habit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitService.createByCurrentUser(habit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(habitService.readById(id));
    }

    @GetMapping("/{zone}/{name}")
    public ResponseEntity<HabitDto> readByZoneAndName(@PathVariable String zone, @PathVariable String name) {
        return ResponseEntity.ok(habitService.readByZoneAndName(zone, name));
    }

    @GetMapping
    public ResponseEntity<List<HabitDto>> readAll(@RequestParam(name = "zone", required = false) Long zone) {
        return ResponseEntity.ok(habitService.readAll(zone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitDto> update(@PathVariable Long id, @RequestBody @Valid HabitDto habit) {
        return ResponseEntity.ok(habitService.update(id, habit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}