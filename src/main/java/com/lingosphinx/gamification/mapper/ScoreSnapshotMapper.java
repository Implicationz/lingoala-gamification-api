package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.ScoreSnapshot;
import com.lingosphinx.gamification.dto.ScoreSnapshotDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScoreSnapshotMapper {
    ScoreSnapshotDto toDto(ScoreSnapshot entity);
    ScoreSnapshot toEntity(ScoreSnapshotDto dto);
    

    void toEntityFromDto(ScoreSnapshotDto scoreSnapshotDto, @MappingTarget  ScoreSnapshot existingScoreSnapshot);
}