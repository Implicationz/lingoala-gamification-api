package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.service.GoalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
@Tag(name = "Goal API")
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalDto> create(@RequestBody @Valid GoalDto goal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.create(goal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.readById(id));
    }

    @GetMapping("/{type}/{reference}")
    public ResponseEntity<GoalDto> readByTypeAndReference(@PathVariable String type, @PathVariable String reference) {
        return ResponseEntity.ok(goalService.readByTypeNameAndReference(type, reference));
    }

    @GetMapping
    public ResponseEntity<List<GoalDto>> readAll(@RequestParam(required=false) String zone, @RequestParam(required=false) String type) {
        if(zone != null && type != null) {
            return ResponseEntity.ok(goalService.readAllByZoneNameAndTypeName(zone, type));
        }
        return ResponseEntity.ok(goalService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDto> update(@PathVariable Long id, @RequestBody @Valid GoalDto goal) {
        return ResponseEntity.ok(goalService.update(id, goal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        goalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}