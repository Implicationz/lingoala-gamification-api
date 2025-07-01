package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;

import java.util.List;

public interface StreakService {
    GoalDefinitionDto create(GoalDefinitionDto goalDefinition);
    GoalDefinitionDto readById(Long id);
    List<GoalDefinitionDto> readAll();
    GoalDefinitionDto update(Long id, GoalDefinitionDto goalDefinition);
    void delete(Long id);
}
