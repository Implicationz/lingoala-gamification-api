package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.ScoreType;
import com.lingosphinx.gamification.dto.ScoreTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScoreTypeMapper {

    ScoreTypeDto toDto(ScoreType entity);
    ScoreType toEntity(ScoreTypeDto dto);

    void toEntityFromDto(ScoreTypeDto goalTypeDto, @MappingTarget ScoreType existingScoreType);
}