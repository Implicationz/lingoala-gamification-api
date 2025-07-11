package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.GoalTypeDto;

import java.util.List;

public interface GoalTypeService {
    GoalTypeDto create(GoalTypeDto goalType);
    GoalTypeDto readById(Long id);
    List<GoalTypeDto> readAll();
    GoalTypeDto update(Long id, GoalTypeDto goalType);
    void delete(Long id);

    GoalTypeDto readByName(String name);
}
