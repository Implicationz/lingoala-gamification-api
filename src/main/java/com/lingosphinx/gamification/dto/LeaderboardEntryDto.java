package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaderboardEntryDto {

    private Long id;
    private LeaderboardDto leaderboard;
    private ContestantDto contestant;
    private ExperienceValue progress = ExperienceValue.ZERO;

}
