package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Streak;
import com.lingosphinx.gamification.dto.StreakDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StreakMapper {
    StreakDto toDto(Streak entity);
    Streak toEntity(StreakDto dto);

    void toEntityFromDto(StreakDto streakDto, @MappingTarget  Streak existingStreak);
}