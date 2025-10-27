package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.LeaderboardDto;
import com.lingosphinx.gamification.dto.LeaderboardSearch;
import com.lingosphinx.gamification.service.LeaderboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
@Tag(name = "Leaderboard API")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @PostMapping("/search")
    public ResponseEntity<LeaderboardSearch> search(@RequestBody @Valid LeaderboardSearch search) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leaderboardService.search(search));
    }

    @PostMapping
    public ResponseEntity<LeaderboardDto> create(@RequestBody @Valid LeaderboardDto leaderboard) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leaderboardService.create(leaderboard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaderboardDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(leaderboardService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<LeaderboardDto>> readAll() {
        return ResponseEntity.ok(leaderboardService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaderboardDto> update(@PathVariable Long id, @RequestBody @Valid LeaderboardDto leaderboard) {
        return ResponseEntity.ok(leaderboardService.update(id, leaderboard));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        leaderboardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}