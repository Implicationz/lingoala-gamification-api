package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.GoalType;
import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDefinitionRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalDefinitionMapper {

    @Mapping(target = "parent.children", ignore = true)
    @Mapping(target = "parent.parent", ignore = true)
    GoalDefinitionDto toDto(GoalDefinition entity);
    GoalDefinition toEntity(GoalDefinitionDto dto);

    default List<GoalDefinitionDto> toDtoList(List<GoalDefinition> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::childToDto).toList();
    }

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    GoalDefinitionDto childToDto(GoalDefinition entity);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    void updateEntityFromDto(GoalDefinitionDto goalDefinitionDto, @MappingTarget GoalDefinition existingGoalDefinition);

    default GoalDefinition toEntityFromRegistration(GoalDefinitionRegistrationDto goalDefinitionRegistration) {
        return GoalDefinition.builder()
                .type(GoalType.builder().name(goalDefinitionRegistration.getType()).build())
                .zone(GoalZone.builder().name(goalDefinitionRegistration.getZone()).build())
                .reference(goalDefinitionRegistration.getReference())
                .name(goalDefinitionRegistration.getName())
                .worth(goalDefinitionRegistration.getWorth())
                .target(goalDefinitionRegistration.getTarget())
                .image(goalDefinitionRegistration.getImage())
                .experience(goalDefinitionRegistration.getExperience())
                .build();
    }
}