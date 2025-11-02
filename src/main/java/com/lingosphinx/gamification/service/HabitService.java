package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.dto.HabitActivationDto;
import com.lingosphinx.gamification.dto.HabitDto;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HabitService {
    HabitDto create(HabitDto habit);

    HabitDto createByCurrentUser(HabitDto habitDto);

    HabitDto readById(Long id);
    HabitDto readByZoneAndName(String zone, String name);

    @Transactional(readOnly = true)
    List<HabitDto> readAll(Long zoneId);

    HabitDto update(Long id, HabitDto habit);
    void delete(Long id);

    void resetAll(IanaTimeZone ianaTimeZone);

    HabitDto activate(@Valid HabitActivationDto habitDefinitionActivation);
}
