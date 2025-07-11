package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.service.GoalDefinitionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goalDefinition")
@RequiredArgsConstructor
@Tag(name = "GoalDefinition API")
public class GoalDefinitionController {

    private final GoalDefinitionService goalDefinitionService;

    @PostMapping
    public ResponseEntity<GoalDefinitionDto> create(@RequestBody @Valid GoalDefinitionDto goalDefinition) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalDefinitionService.create(goalDefinition));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDefinitionDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(goalDefinitionService.readById(id));
    }

    @GetMapping("/{type}/{reference}")
    public ResponseEntity<GoalDefinitionDto> readByTypeAndReference(@PathVariable String type, @PathVariable String reference) {
        return ResponseEntity.ok(goalDefinitionService.readByTypeNameAndReference(type, reference));
    }

    @GetMapping
    public ResponseEntity<List<GoalDefinitionDto>> readAll() {
        return ResponseEntity.ok(goalDefinitionService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDefinitionDto> update(@PathVariable Long id, @RequestBody @Valid GoalDefinitionDto goalDefinition) {
        return ResponseEntity.ok(goalDefinitionService.update(id, goalDefinition));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        goalDefinitionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}