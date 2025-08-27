package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.ScoreDefinition;
import com.lingosphinx.gamification.dto.ScoreDefinitionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScoreDefinitionMapper {

    ScoreDefinitionDto toDto(ScoreDefinition entity);
    ScoreDefinition toEntity(ScoreDefinitionDto dto);

    void updateEntityFromDto(ScoreDefinitionDto scoreDefinitionDto, @MappingTarget ScoreDefinition existingScoreDefinition);
}