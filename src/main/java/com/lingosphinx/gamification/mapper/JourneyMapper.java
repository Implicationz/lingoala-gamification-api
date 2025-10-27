package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Journey;
import com.lingosphinx.gamification.domain.JourneyEntry;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.JourneyDto;
import com.lingosphinx.gamification.dto.JourneyEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JourneyMapper {
    JourneyDto toDto(Journey entity);
    Journey toEntity(JourneyDto dto);

    @Mapping(target = "experiences", ignore = true)
    ContestantDto toDto(Contestant entity);
    @Mapping(target = "experiences", ignore = true)
    Contestant toEntity(ContestantDto dto);

    @Mapping(target = "journey", ignore = true)
    JourneyEntryDto toDto(JourneyEntry entity);
    @Mapping(target = "journey", ignore = true)
    JourneyEntry toEntity(JourneyEntryDto dto);

    void toEntityFromDto(JourneyDto journeyDto, @MappingTarget  Journey existingJourney);
}