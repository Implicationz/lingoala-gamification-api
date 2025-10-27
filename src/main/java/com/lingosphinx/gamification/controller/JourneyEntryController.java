package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.JourneyEntryDto;
import com.lingosphinx.gamification.service.JourneyEntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journey-entry")
@RequiredArgsConstructor
@Tag(name = "JourneyEntry API")
public class JourneyEntryController {

    private final JourneyEntryService journeyEntryService;

    @PostMapping
    public ResponseEntity<JourneyEntryDto> create(@RequestBody @Valid JourneyEntryDto journeyEntry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(journeyEntryService.create(journeyEntry));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JourneyEntryDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(journeyEntryService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<JourneyEntryDto>> readAll() {
        return ResponseEntity.ok(journeyEntryService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JourneyEntryDto> update(@PathVariable Long id, @RequestBody @Valid JourneyEntryDto journeyEntry) {
        return ResponseEntity.ok(journeyEntryService.update(id, journeyEntry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        journeyEntryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}