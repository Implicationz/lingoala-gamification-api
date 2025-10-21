package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.GoalType;
import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDefinitionRegistrationDto;
import com.lingosphinx.gamification.dto.ObjectiveDefinitionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GoalDefinitionMapper {


    GoalDefinitionDto toDto(GoalDefinition entity);
    GoalDefinition toEntity(GoalDefinitionDto dto);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "child.objectives", ignore = true)
    ObjectiveDefinitionDto toDto(ObjectiveDefinition entity);
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "child.objectives", ignore = true)
    ObjectiveDefinition toEntity(ObjectiveDefinitionDto dto);


    @Mapping(target = "objectives", ignore = true)
    GoalDefinitionDto childToDto(GoalDefinition entity);

    @Mapping(target = "objectives", ignore = true)
    void updateEntityFromDto(GoalDefinitionDto goalDefinitionDto, @MappingTarget GoalDefinition existingGoalDefinition);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "child.objectives", ignore = true)
    void updateEntityFromDto(ObjectiveDefinitionDto objectiveDefinitionDto, @MappingTarget ObjectiveDefinition existingObjectiveDefinition);

    default GoalDefinition toEntityFromRegistration(GoalDefinitionRegistrationDto goalDefinitionRegistration) {
        return GoalDefinition.builder()
                .type(GoalType.builder().name(goalDefinitionRegistration.getType()).build())
                .zone(GoalZone.builder().name(goalDefinitionRegistration.getZone()).build())
                .reference(goalDefinitionRegistration.getReference())
                .name(goalDefinitionRegistration.getName())
                .target(goalDefinitionRegistration.getTarget())
                .image(goalDefinitionRegistration.getImage())
                .experience(goalDefinitionRegistration.getExperience())
                .build();
    }
}