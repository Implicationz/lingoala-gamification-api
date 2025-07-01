package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalDefinitionMapper {

    GoalDefinitionDto toDto(GoalDefinition entity);

    List<GoalDefinitionDto> toDtoList(List<GoalDefinition> entities);

    GoalDefinition toEntity(GoalDefinitionDto dto);

    void toEntityFromDto(GoalDefinitionDto goalDefinitionDto, @MappingTarget GoalDefinition existingGoalDefinition);
}