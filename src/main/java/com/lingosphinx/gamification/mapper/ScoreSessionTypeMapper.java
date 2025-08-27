package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.ScoreSessionType;
import com.lingosphinx.gamification.dto.ScoreSessionTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScoreSessionTypeMapper {

    ScoreSessionTypeDto toDto(ScoreSessionType entity);
    ScoreSessionType toEntity(ScoreSessionTypeDto dto);

    void toEntityFromDto(ScoreSessionTypeDto goalTypeDto, @MappingTarget ScoreSessionType existingScoreSessionType);
}