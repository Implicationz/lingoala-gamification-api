package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.StreakProgress;
import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.dto.StreakProgressDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface HabitMapper {
    HabitDto toDto(Habit entity);
    Habit toEntity(HabitDto dto);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    GoalDto toDto(Goal entity);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    Goal toEntity(GoalDto dto);

    @Mapping(target = "streak", ignore = true)
    StreakProgressDto toDto(StreakProgress entity);

    @Mapping(target = "streak", ignore = true)
    StreakProgress toEntity(StreakProgressDto dto);

    void toEntityFromDto(HabitDto goalDto, @MappingTarget  Habit existingHabit);
}