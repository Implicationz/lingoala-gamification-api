package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.dto.ScoreSnapshotDto;

import java.util.List;

public interface ScoreSnapshotService {
    ScoreSnapshotDto create(ScoreSnapshotDto scoreSnapshot);

    ScoreSnapshotDto readById(Long id);
    List<ScoreSnapshotDto> readAll();
    ScoreSnapshotDto update(Long id, ScoreSnapshotDto scoreSnapshot);
    void delete(Long id);

    void regenerateAll(IanaTimeZone ianaTimeZone);
}
