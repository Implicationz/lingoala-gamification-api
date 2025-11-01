package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.ScoreProgress;
import com.lingosphinx.gamification.domain.ScoreSession;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.ScoreProgressDto;
import com.lingosphinx.gamification.dto.ScoreSessionDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ScoreSessionMapper {
    ScoreSessionDto toDto(ScoreSession entity);
    ScoreSession toEntity(ScoreSessionDto dto);

    @Mapping(target = "experiences", ignore = true)
    ContestantDto toDto(Contestant entity);
    @Mapping(target = "experiences", ignore = true)
    Contestant toEntity(ContestantDto dto);

    @Mapping(target = "session", ignore = true)
    @Mapping(target = "score.snapshots", ignore = true)
    ScoreProgressDto toDto(ScoreProgress entity);
    @Mapping(target = "session", ignore = true)
    @Mapping(target = "score.snapshots", ignore = true)
    ScoreProgress toEntity(ScoreProgressDto dto);

    @Mapping(target = "events", ignore = true)
    void toEntityFromDto(ScoreSessionDto scoreSessionDto, @MappingTarget ScoreSession existingScoreSession);

    @AfterMapping
    default void setParentSession(@MappingTarget ScoreSession scoreSession) {
        if (scoreSession.getEvents() != null) {
            scoreSession.getEvents().forEach(scoreProgress -> scoreProgress.setSession(scoreSession));
        }
    }
}