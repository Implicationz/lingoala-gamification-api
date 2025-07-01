package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.dto.GoalDto;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {GoalDefinitionMapper.class})
public interface GoalMapper {
    GoalDto toDto(Goal entity);
    Goal toEntity(GoalDto dto);

    List<GoalDto> toDtoList(List<Goal> entities);

    void toEntityFromDto(GoalDto goalDto, @MappingTarget  Goal existingGoal);
}