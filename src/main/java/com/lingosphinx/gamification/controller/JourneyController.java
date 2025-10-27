package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.JourneyActivationDto;
import com.lingosphinx.gamification.dto.JourneyDto;
import com.lingosphinx.gamification.service.JourneyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journey")
@RequiredArgsConstructor
@Tag(name = "Journey API")
public class JourneyController {

    private final JourneyService journeyService;

    @PostMapping("/activation")
    public ResponseEntity<JourneyActivationDto> activation(@RequestBody @Valid JourneyActivationDto journeyActivation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(journeyService.activate(journeyActivation));
    }

    @PostMapping
    public ResponseEntity<JourneyDto> create(@RequestBody @Valid JourneyDto journey) {
        return ResponseEntity.status(HttpStatus.CREATED).body(journeyService.create(journey));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JourneyDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(journeyService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<JourneyDto>> readAll() {
        return ResponseEntity.ok(journeyService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JourneyDto> update(@PathVariable Long id, @RequestBody @Valid JourneyDto journey) {
        return ResponseEntity.ok(journeyService.update(id, journey));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        journeyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}