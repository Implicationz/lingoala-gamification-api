package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.GoalTypeDto;
import com.lingosphinx.gamification.service.GoalTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal-type")
@RequiredArgsConstructor
@Tag(name = "GoalType API")
public class GoalTypeController {

    private final GoalTypeService goalTypeService;

    @PostMapping
    public ResponseEntity<GoalTypeDto> create(@RequestBody @Valid GoalTypeDto goal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalTypeService.create(goal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalTypeDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(goalTypeService.readById(id));
    }

    @GetMapping(value = "", params = "name")
    public ResponseEntity<GoalTypeDto> readByName(@RequestParam String name) {
        return ResponseEntity.ok(goalTypeService.readByName(name));
    }

    @GetMapping(value = "", params = "!name")
    public ResponseEntity<List<GoalTypeDto>> readAll() {
        return ResponseEntity.ok(goalTypeService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalTypeDto> update(@PathVariable Long id, @RequestBody @Valid GoalTypeDto goalType) {
        return ResponseEntity.ok(goalTypeService.update(id, goalType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        goalTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}