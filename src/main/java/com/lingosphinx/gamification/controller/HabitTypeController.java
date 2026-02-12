package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.HabitTypeDto;
import com.lingosphinx.gamification.service.HabitTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit-type")
@RequiredArgsConstructor
@Tag(name = "HabitType API")
public class HabitTypeController {

    private final HabitTypeService habitTypeService;

    @PostMapping
    public ResponseEntity<HabitTypeDto> create(@RequestBody @Valid HabitTypeDto habit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitTypeService.create(habit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitTypeDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(habitTypeService.readById(id));
    }

    @GetMapping(value = "", params = "name")
    public ResponseEntity<HabitTypeDto> readByName(@RequestParam String name) {
        return ResponseEntity.ok(habitTypeService.readByName(name));
    }

    @GetMapping(value = "", params = "!name")
    public ResponseEntity<List<HabitTypeDto>> readAll() {
        return ResponseEntity.ok(habitTypeService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitTypeDto> update(@PathVariable Long id, @RequestBody @Valid HabitTypeDto habitType) {
        return ResponseEntity.ok(habitTypeService.update(id, habitType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}