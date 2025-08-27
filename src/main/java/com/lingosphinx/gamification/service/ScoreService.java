package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreActivationDto;
import com.lingosphinx.gamification.dto.ScoreDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ScoreService {
    ScoreDto create(ScoreDto score);

    ScoreDto readById(Long id);
    List<ScoreDto> readAll();
    ScoreDto update(Long id, ScoreDto score);
    void delete(Long id);

    ScoreDto activate(@Valid ScoreActivationDto scoreActivation);

    ScoreDto readByZoneAndType(String zone, String type);
}
