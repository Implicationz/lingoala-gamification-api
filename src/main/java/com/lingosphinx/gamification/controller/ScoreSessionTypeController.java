package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ScoreSessionTypeDto;
import com.lingosphinx.gamification.service.ScoreSessionTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score-session-type")
@RequiredArgsConstructor
@Tag(name = "ScoreSessionType API")
public class ScoreSessionTypeController {

    private final ScoreSessionTypeService scoreSessionTypeService;

    @PostMapping
    public ResponseEntity<ScoreSessionTypeDto> create(@RequestBody @Valid ScoreSessionTypeDto score) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreSessionTypeService.create(score));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreSessionTypeDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(scoreSessionTypeService.readById(id));
    }

    @GetMapping(value = "", params = "name")
    public ResponseEntity<ScoreSessionTypeDto> readByName(@RequestParam String name) {
        return ResponseEntity.ok(scoreSessionTypeService.readByName(name));
    }

    @GetMapping(value = "", params = "!name")
    public ResponseEntity<List<ScoreSessionTypeDto>> readAll() {
        return ResponseEntity.ok(scoreSessionTypeService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreSessionTypeDto> update(@PathVariable Long id, @RequestBody @Valid ScoreSessionTypeDto scoreSessionType) {
        return ResponseEntity.ok(scoreSessionTypeService.update(id, scoreSessionType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scoreSessionTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}