package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.HabitDto;

import java.util.List;

public interface HabitService {
    HabitDto create(HabitDto habit);
    HabitDto readById(Long id);
    HabitDto readByTypeNameAndReference(String type, String reference);
    List<HabitDto> readAll();
    HabitDto update(Long id, HabitDto habit);
    void delete(Long id);

    void resetAll();
}
