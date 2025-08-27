package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.service.ContestantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contestant")
@RequiredArgsConstructor
@Tag(name = "Contestant API")
public class ContestantController {

    private final ContestantService contestantService;

    @PostMapping
    public ResponseEntity<ContestantDto> create(@RequestBody @Valid ContestantDto contestant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contestantService.create(contestant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContestantDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(contestantService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<ContestantDto>> readAll() {
        return ResponseEntity.ok(contestantService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContestantDto> update(@PathVariable Long id, @RequestBody @Valid ContestantDto contestant) {
        return ResponseEntity.ok(contestantService.update(id, contestant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contestantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}