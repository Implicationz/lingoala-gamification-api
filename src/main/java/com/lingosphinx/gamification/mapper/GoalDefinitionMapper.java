package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalDefinitionMapper {

    @Mapping(target = "parent.children", ignore = true)
    @Mapping(target = "parent.parent", ignore = true)
    GoalDefinitionDto toDto(GoalDefinition entity);

    default List<GoalDefinitionDto> toDtoList(List<GoalDefinition> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::childToDto).toList();
    }

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    GoalDefinitionDto childToDto(GoalDefinition entity);


    GoalDefinition toEntity(GoalDefinitionDto dto);

    void updateEntityFromDto(GoalDefinitionDto goalDefinitionDto, @MappingTarget GoalDefinition existingGoalDefinition);
}