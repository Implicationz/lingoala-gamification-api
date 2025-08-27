package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.ScoreTypeDto;

import java.util.List;

public interface ScoreTypeService {
    ScoreTypeDto create(ScoreTypeDto scoreType);
    ScoreTypeDto readById(Long id);
    List<ScoreTypeDto> readAll();
    ScoreTypeDto update(Long id, ScoreTypeDto scoreType);
    void delete(Long id);

    ScoreTypeDto readByName(String name);
}
