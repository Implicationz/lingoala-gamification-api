package com.lingosphinx.gamification.controller;

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

    @PostMapping
    public ResponseEntity<HabitDto> create(@RequestBody @Valid HabitDto habit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitService.create(habit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(habitService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<HabitDto>> readAll() {
        return ResponseEntity.ok(habitService.readAll());
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