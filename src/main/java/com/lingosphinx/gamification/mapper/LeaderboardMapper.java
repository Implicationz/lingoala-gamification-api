package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Leaderboard;
import com.lingosphinx.gamification.domain.LeaderboardEntry;
import com.lingosphinx.gamification.dto.ContestantDto;
import com.lingosphinx.gamification.dto.LeaderboardDto;
import com.lingosphinx.gamification.dto.LeaderboardEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LeaderboardMapper {
    LeaderboardDto toDto(Leaderboard entity);
    Leaderboard toEntity(LeaderboardDto dto);

    @Mapping(target = "experiences", ignore = true)
    ContestantDto toDto(Contestant entity);
    @Mapping(target = "experiences", ignore = true)
    Contestant toEntity(ContestantDto dto);

    @Mapping(target = "leaderboard", ignore = true)
    LeaderboardEntryDto toDto(LeaderboardEntry entity);
    @Mapping(target = "leaderboard", ignore = true)
    LeaderboardEntry toEntity(LeaderboardEntryDto dto);

    void toEntityFromDto(LeaderboardDto journeyDto, @MappingTarget  Leaderboard existingLeaderboard);
}