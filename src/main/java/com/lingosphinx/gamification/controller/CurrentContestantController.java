package com.lingosphinx.gamification.controller;

import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.service.ContestantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me/contestant")
@RequiredArgsConstructor
@Tag(name = "Contestant API")
public class CurrentContestantController {

    private final ContestantService contestantService;

    @PostMapping
    public ResponseEntity<ContestantDto> register() {
        return ResponseEntity.ok(contestantService.registerCurrent());
    }
}