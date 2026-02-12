package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.HabitType;
import com.lingosphinx.gamification.dto.HabitTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HabitTypeMapper {

    HabitTypeDto toDto(HabitType entity);
    HabitType toEntity(HabitTypeDto dto);

    void toEntityFromDto(HabitTypeDto habitTypeDto, @MappingTarget HabitType existingHabitType);
}