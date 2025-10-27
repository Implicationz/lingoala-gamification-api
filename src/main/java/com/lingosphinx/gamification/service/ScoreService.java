package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreActivationDto;
import com.lingosphinx.gamification.dto.ScoreDto;
import jakarta.validation.Valid;

public interface ScoreService extends CrudService<ScoreDto> {

    ScoreDto activate(@Valid ScoreActivationDto scoreActivation);

    ScoreDto readByZoneAndType(String zone, String type);
}
