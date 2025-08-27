package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreSessionDto;

import java.util.List;

public interface ScoreSessionService {
    ScoreSessionDto create(ScoreSessionDto scoreSession);

    ScoreSessionDto createForCurrentContestant(ScoreSessionDto scoreSessionDto);

    ScoreSessionDto readById(Long id);
    List<ScoreSessionDto> readAll();
    ScoreSessionDto update(Long id, ScoreSessionDto scoreSession);
    void delete(Long id);
}
