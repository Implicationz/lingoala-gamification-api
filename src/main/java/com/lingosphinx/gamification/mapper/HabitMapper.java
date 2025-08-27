package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.StreakProgress;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.dto.StreakProgressDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface HabitMapper {
    HabitDto toDto(Habit entity);
    Habit toEntity(HabitDto dto);

    @Mapping(target = "experiences", ignore = true)
    ContestantDto toDto(Contestant entity);
    @Mapping(target = "experiences", ignore = true)
    Contestant toEntity(ContestantDto dto);

    @Mapping(target = "streak", ignore = true)
    StreakProgressDto toDto(StreakProgress entity);
    @Mapping(target = "streak", ignore = true)
    StreakProgress toEntity(StreakProgressDto dto);

    void toEntityFromDto(HabitDto goalDto, @MappingTarget  Habit existingHabit);
}