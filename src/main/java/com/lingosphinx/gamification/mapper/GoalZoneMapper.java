package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.dto.GoalZoneDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GoalZoneMapper {

    GoalZoneDto toDto(GoalZone entity);
    GoalZone toEntity(GoalZoneDto dto);

    void toEntityFromDto(GoalZoneDto goalZoneDto, @MappingTarget GoalZone existingGoalZone);
}