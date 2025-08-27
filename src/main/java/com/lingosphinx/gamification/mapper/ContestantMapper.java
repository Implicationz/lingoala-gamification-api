package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.ContestantExperience;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.ContestantExperienceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContestantMapper {

    ContestantDto toDto(Contestant entity);
    Contestant toEntity(ContestantDto dto);

    @Mapping(target = "contestant", ignore = true)
    @Mapping(target = "history", ignore = true)
    ContestantExperienceDto toDto(ContestantExperience entity);
    @Mapping(target = "contestant", ignore = true)
    @Mapping(target = "history", ignore = true)
    ContestantExperience toEntity(ContestantExperienceDto dto);

    void toEntityFromDto(ContestantDto contestantDto, @MappingTarget  Contestant existingContestant);
}