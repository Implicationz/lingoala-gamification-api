package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.*;
import com.lingosphinx.gamification.dto.*;
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

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "child.contestant", ignore = true)
    @Mapping(target = "child.objectives", ignore = true)
    @Mapping(target = "propagation", ignore = true)
    ObjectiveDto toDto(Objective entity);
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "child.contestant", ignore = true)
    @Mapping(target = "child.objectives", ignore = true)
    @Mapping(target = "propagation", ignore = true)
    Objective toEntity(ObjectiveDto dto);


    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "child", ignore = true)
    ObjectiveDefinitionDto toDto(ObjectiveDefinition entity);
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "child", ignore = true)
    ObjectiveDefinition toEntity(ObjectiveDefinitionDto dto);

    List<GoalDto> toDtoList(List<Goal> entities);

    @Mapping(target = "objectives", ignore = true)
    void toEntityFromDto(GoalDto goalDto, @MappingTarget  Goal existingGoal);

}