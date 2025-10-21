package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.GoalProgress;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.dto.GoalProgressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GoalProgressMapper {
    GoalProgressDto toDto(GoalProgress entity);
    GoalProgress toEntity(GoalProgressDto dto);

    GoalDto toDto(Goal entity);
    Goal toEntity(GoalDto dto);

    @Mapping(target = "experiences", ignore = true)
    ContestantDto toDto(Contestant entity);
    @Mapping(target = "experiences", ignore = true)
    Contestant toEntity(ContestantDto dto);

    @Mapping(target = "objectives", ignore = true)
    GoalDefinitionDto toDto(GoalDefinition entity);
    @Mapping(target = "objectives", ignore = true)
    GoalDefinition toEntity(GoalDefinitionDto dto);

    void toEntityFromDto(GoalProgressDto goalProgressDto, @MappingTarget GoalProgress existingGoalProgress);
}