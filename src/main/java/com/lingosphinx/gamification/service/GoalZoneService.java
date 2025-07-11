package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.GoalZoneDto;

import java.util.List;

public interface GoalZoneService {
    GoalZoneDto create(GoalZoneDto goalZone);
    GoalZoneDto readById(Long id);
    List<GoalZoneDto> readAll();
    GoalZoneDto update(Long id, GoalZoneDto goalZone);
    void delete(Long id);

    GoalZoneDto readByName(String name);
}
