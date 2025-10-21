package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Objective;
import com.lingosphinx.gamification.dto.ObjectiveDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectiveMapper {
    ObjectiveDto toDto(Objective entity);
    Objective toEntity(ObjectiveDto dto);
}
