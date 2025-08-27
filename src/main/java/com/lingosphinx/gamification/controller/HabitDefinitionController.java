package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.HabitDefinitionDto;
import com.lingosphinx.gamification.dto.HabitDefinitionRegistrationDto;
import com.lingosphinx.gamification.service.HabitDefinitionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit-definition")
@RequiredArgsConstructor
@Tag(name = "HabitDefinition API")
public class HabitDefinitionController {

    private final HabitDefinitionService habitDefinitionService;

    @PostMapping("/registration")
    public ResponseEntity<HabitDefinitionDto> registration(@RequestBody @Valid HabitDefinitionRegistrationDto habitDefinitionRegistration) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitDefinitionService.register(habitDefinitionRegistration));
    }

    @PostMapping
    public ResponseEntity<HabitDefinitionDto> create(@RequestBody @Valid HabitDefinitionDto habitDefinition) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitDefinitionService.create(habitDefinition));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitDefinitionDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(habitDefinitionService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<HabitDefinitionDto>> readAll() {
        return ResponseEntity.ok(habitDefinitionService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitDefinitionDto> update(@PathVariable Long id, @RequestBody @Valid HabitDefinitionDto habitDefinition) {
        return ResponseEntity.ok(habitDefinitionService.update(id, habitDefinition));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitDefinitionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}