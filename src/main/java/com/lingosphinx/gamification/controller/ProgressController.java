package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ProgressDto;
import com.lingosphinx.gamification.service.ProgressService;
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
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping
    public ResponseEntity<ProgressDto> create(@RequestBody @Valid ProgressDto progress) {
        return ResponseEntity.status(HttpStatus.CREATED).body(progressService.create(progress));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(progressService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProgressDto>> readAll() {
        return ResponseEntity.ok(progressService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressDto> update(@PathVariable Long id, @RequestBody @Valid ProgressDto progress) {
        return ResponseEntity.ok(progressService.update(id, progress));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        progressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}