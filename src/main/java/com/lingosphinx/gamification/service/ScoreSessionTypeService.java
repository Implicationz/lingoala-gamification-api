package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.ScoreSessionTypeDto;

import java.util.List;

public interface ScoreSessionTypeService {
    ScoreSessionTypeDto create(ScoreSessionTypeDto scoreSessionType);
    ScoreSessionTypeDto readById(Long id);
    List<ScoreSessionTypeDto> readAll();
    ScoreSessionTypeDto update(Long id, ScoreSessionTypeDto scoreSessionType);
    void delete(Long id);

    ScoreSessionTypeDto readByName(String name);
}
