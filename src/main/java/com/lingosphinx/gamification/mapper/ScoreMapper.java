package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Score;
import com.lingosphinx.gamification.domain.ScoreSnapshot;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.ScoreDto;
import com.lingosphinx.gamification.dto.ScoreSnapshotDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScoreMapper {
    ScoreDto toDto(Score entity);
    Score toEntity(ScoreDto dto);

    @Mapping(target = "experiences", ignore = true)
    ContestantDto toDto(Contestant entity);
    @Mapping(target = "experiences", ignore = true)
    Contestant toEntity(ContestantDto dto);

    @Mapping(target = "score", ignore = true)
    ScoreSnapshotDto toDto(ScoreSnapshot entity);
    @Mapping(target = "score", ignore = true)
    ScoreSnapshot toEntity(ScoreSnapshotDto dto);
    
    void toEntityFromDto(ScoreDto scoreDto, @MappingTarget  Score existingScore);
}