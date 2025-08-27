package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.HabitDefinitionDto;
import com.lingosphinx.gamification.dto.HabitDefinitionRegistrationDto;
import jakarta.validation.Valid;

import java.util.List;

public interface HabitDefinitionService {
    HabitDefinitionDto create(HabitDefinitionDto habitDefinition);
    HabitDefinitionDto readById(Long id);
    List<HabitDefinitionDto> readAll();
    HabitDefinitionDto update(Long id, HabitDefinitionDto habitDefinition);
    void delete(Long id);

    HabitDefinitionDto register(@Valid HabitDefinitionRegistrationDto habitDefinitionRegistration);
}
