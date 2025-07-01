package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.GoalDto;

import java.util.List;

public interface GoalService {
    GoalDto create(GoalDto goal);
    GoalDto readById(Long id);
    List<GoalDto> readAll();
    GoalDto update(Long id, GoalDto goal);
    void delete(Long id);
}
