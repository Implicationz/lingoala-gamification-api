package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.GoalDto;

import java.util.List;

public interface GoalService {
    GoalDto create(GoalDto goal);

    GoalDto createByCurrentUser(GoalDto goalDto);

    GoalDto readById(Long id);
    List<GoalDto> readAll();
    GoalDto update(Long id, GoalDto goal);
    void delete(Long id);

    GoalDto readByTypeNameAndReference(String type, String reference);

    List<GoalDto> readAllByZoneNameAndTypeName(String zone, String type);
}
