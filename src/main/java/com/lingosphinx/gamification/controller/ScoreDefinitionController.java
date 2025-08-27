package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ScoreDefinitionDto;
import com.lingosphinx.gamification.service.ScoreDefinitionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score-definition")
@RequiredArgsConstructor
@Tag(name = "ScoreDefinition API")
public class ScoreDefinitionController {

    private final ScoreDefinitionService scoreDefinitionService;

    @PostMapping
    public ResponseEntity<ScoreDefinitionDto> create(@RequestBody @Valid ScoreDefinitionDto scoreDefinition) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreDefinitionService.create(scoreDefinition));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreDefinitionDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(scoreDefinitionService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<ScoreDefinitionDto>> readAll() {
        return ResponseEntity.ok(scoreDefinitionService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreDefinitionDto> update(@PathVariable Long id, @RequestBody @Valid ScoreDefinitionDto scoreDefinition) {
        return ResponseEntity.ok(scoreDefinitionService.update(id, scoreDefinition));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scoreDefinitionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}