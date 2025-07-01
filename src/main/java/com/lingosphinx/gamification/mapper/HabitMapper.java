package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.dto.HabitDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitMapper {
    HabitDto toDto(Habit entity);
    Habit toEntity(HabitDto dto);
    

    void toEntityFromDto(HabitDto goalDto, @MappingTarget  Habit existingHabit);
}