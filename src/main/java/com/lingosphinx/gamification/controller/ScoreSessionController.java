package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ScoreSessionDto;
import com.lingosphinx.gamification.service.ScoreSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score-session")
@RequiredArgsConstructor
@Tag(name = "ScoreSession API")
public class ScoreSessionController {

    private final ScoreSessionService scoreService;

    @PostMapping
    public ResponseEntity<ScoreSessionDto> create(@RequestBody @Valid ScoreSessionDto score) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreService.createForCurrentContestant(score));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreSessionDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(scoreService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<ScoreSessionDto>> readAll() {
        return ResponseEntity.ok(scoreService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreSessionDto> update(@PathVariable Long id, @RequestBody @Valid ScoreSessionDto score) {
        return ResponseEntity.ok(scoreService.update(id, score));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}