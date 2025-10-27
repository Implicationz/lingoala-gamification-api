package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LeaderboardSearchEntry {

    @EqualsAndHashCode.Include
    private Long id;
    private Long rank;
    private LeaderboardDto leaderboard;
    private ContestantDto contestant;
    private ExperienceValue progress = ExperienceValue.ZERO;

}
