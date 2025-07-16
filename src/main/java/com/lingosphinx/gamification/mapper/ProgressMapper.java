package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Progress;
import com.lingosphinx.gamification.dto.ProgressDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProgressMapper {
    ProgressDto toDto(Progress entity);
    Progress toEntity(ProgressDto dto);
    void toEntityFromDto(ProgressDto goalDto, @MappingTarget  Progress existingProgress);
}