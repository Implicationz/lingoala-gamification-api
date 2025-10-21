package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    GoalDto toDto(Goal entity);
    Goal toEntity(GoalDto dto);

    @Mapping(target = "objectives", ignore = true)
    GoalDefinitionDto toDto(GoalDefinition entity);
    @Mapping(target = "objectives", ignore = true)
    GoalDefinition toEntity(GoalDefinitionDto dto);

    @Mapping(target = "experiences", ignore = true)
    ContestantDto toDto(Contestant entity);
    @Mapping(target = "experiences", ignore = true)
    Contestant toEntity(ContestantDto dto);

    List<GoalDto> toDtoList(List<Goal> entities);

    @Mapping(target = "objectives", ignore = true)
    void toEntityFromDto(GoalDto goalDto, @MappingTarget  Goal existingGoal);

}