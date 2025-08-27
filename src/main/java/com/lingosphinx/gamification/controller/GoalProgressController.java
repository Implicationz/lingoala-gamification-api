package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.GoalProgressDto;
import com.lingosphinx.gamification.service.GoalProgressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
@Tag(name = "Progress API")
public class GoalProgressController {

    private final GoalProgressService goalProgressService;

    @PostMapping
    public ResponseEntity<GoalProgressDto> create(@RequestBody @Valid GoalProgressDto progress) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalProgressService.create(progress));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalProgressDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(goalProgressService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<GoalProgressDto>> readAll() {
        return ResponseEntity.ok(goalProgressService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalProgressDto> update(@PathVariable Long id, @RequestBody @Valid GoalProgressDto progress) {
        return ResponseEntity.ok(goalProgressService.update(id, progress));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        goalProgressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}