package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.domain.HabitDefinition;
import com.lingosphinx.gamification.dto.HabitDefinitionDto;
import com.lingosphinx.gamification.dto.HabitDefinitionRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HabitDefinitionMapper {

    HabitDefinitionDto toDto(HabitDefinition entity);
    HabitDefinition toEntity(HabitDefinitionDto dto);

    void updateEntityFromDto(HabitDefinitionDto habitDefinitionDto, @MappingTarget HabitDefinition existingHabitDefinition);

    default HabitDefinition toEntityFromRegistration(HabitDefinitionRegistrationDto habitDefinitionRegistration) {
        return HabitDefinition.builder()
                .zone(GoalZone.builder().name(habitDefinitionRegistration.getZone()).build())
                .name(habitDefinitionRegistration.getName())
                .build();
    }
}