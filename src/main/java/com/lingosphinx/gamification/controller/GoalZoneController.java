package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.GoalZoneDto;
import com.lingosphinx.gamification.service.GoalZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal-zone")
@RequiredArgsConstructor
@Tag(name = "GoalZone API")
public class GoalZoneController {

    private final GoalZoneService goalZoneService;

    @PostMapping
    public ResponseEntity<GoalZoneDto> create(@RequestBody @Valid GoalZoneDto goal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalZoneService.create(goal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalZoneDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(goalZoneService.readById(id));
    }

    @GetMapping(value = "", params = "name")
    public ResponseEntity<GoalZoneDto> readByName(@RequestParam String name) {
        return ResponseEntity.ok(goalZoneService.readByName(name));
    }

    @GetMapping(value = "", params = "!name")
    public ResponseEntity<List<GoalZoneDto>> readAll() {
        return ResponseEntity.ok(goalZoneService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalZoneDto> update(@PathVariable Long id, @RequestBody @Valid GoalZoneDto goalZone) {
        return ResponseEntity.ok(goalZoneService.update(id, goalZone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        goalZoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}