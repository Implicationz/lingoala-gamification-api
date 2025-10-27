package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.JourneyEntry;
import com.lingosphinx.gamification.dto.JourneyEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JourneyEntryMapper {
    JourneyEntryDto toDto(JourneyEntry entity);
    JourneyEntry toEntity(JourneyEntryDto dto);

    void toEntityFromDto(JourneyEntryDto journeyEntryDto, @MappingTarget  JourneyEntry existingJourneyEntry);
}