package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.domain.GoalProgress;
import com.lingosphinx.gamification.dto.GoalProgressDto;

import java.util.List;

public interface GoalProgressService {
    GoalProgressDto create(GoalProgressDto progress);
    GoalProgressDto readById(Long id);
    List<GoalProgressDto> readAll();
    GoalProgressDto update(Long id, GoalProgressDto progress);
    void delete(Long id);

    void propagate(GoalProgress goalProgress);
}
