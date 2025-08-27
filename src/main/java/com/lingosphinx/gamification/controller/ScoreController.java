package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ScoreActivationDto;
import com.lingosphinx.gamification.dto.ScoreDto;
import com.lingosphinx.gamification.service.ScoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
@Tag(name = "Score API")
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/activation")
    public ResponseEntity<ScoreDto> activation(@RequestBody @Valid ScoreActivationDto scoreActivation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreService.activate(scoreActivation));
    }

    @PostMapping
    public ResponseEntity<ScoreDto> create(@RequestBody @Valid ScoreDto score) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreService.create(score));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(scoreService.readById(id));
    }

    @GetMapping("/{zone}/{type}")
    public ResponseEntity<ScoreDto> readByZoneAndType(@PathVariable String zone, @PathVariable String type) {
        return ResponseEntity.ok(scoreService.readByZoneAndType(zone, type));
    }

    @GetMapping
    public ResponseEntity<List<ScoreDto>> readAll() {
        return ResponseEntity.ok(scoreService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreDto> update(@PathVariable Long id, @RequestBody @Valid ScoreDto score) {
        return ResponseEntity.ok(scoreService.update(id, score));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}