package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    GoalDto toDto(Goal entity);
    Goal toEntity(GoalDto dto);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    GoalDefinitionDto toDto(GoalDefinition entity);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    GoalDefinition toEntity(GoalDefinitionDto dto);

    List<GoalDto> toDtoList(List<Goal> entities);

    void toEntityFromDto(GoalDto goalDto, @MappingTarget  Goal existingGoal);
}