package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.GoalType;
import com.lingosphinx.gamification.dto.GoalTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GoalTypeMapper {

    GoalTypeDto toDto(GoalType entity);
    GoalType toEntity(GoalTypeDto dto);

    void toEntityFromDto(GoalTypeDto goalTypeDto, @MappingTarget GoalType existingGoalType);
}