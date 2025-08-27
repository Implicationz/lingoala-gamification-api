package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ScoreTypeDto;
import com.lingosphinx.gamification.service.ScoreTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score-type")
@RequiredArgsConstructor
@Tag(name = "ScoreType API")
public class ScoreTypeController {

    private final ScoreTypeService scoreTypeService;

    @PostMapping
    public ResponseEntity<ScoreTypeDto> create(@RequestBody @Valid ScoreTypeDto score) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreTypeService.create(score));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreTypeDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(scoreTypeService.readById(id));
    }

    @GetMapping(value = "", params = "name")
    public ResponseEntity<ScoreTypeDto> readByName(@RequestParam String name) {
        return ResponseEntity.ok(scoreTypeService.readByName(name));
    }

    @GetMapping(value = "", params = "!name")
    public ResponseEntity<List<ScoreTypeDto>> readAll() {
        return ResponseEntity.ok(scoreTypeService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreTypeDto> update(@PathVariable Long id, @RequestBody @Valid ScoreTypeDto scoreType) {
        return ResponseEntity.ok(scoreTypeService.update(id, scoreType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scoreTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}