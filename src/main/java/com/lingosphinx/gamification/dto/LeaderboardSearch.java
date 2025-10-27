package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.domain.RenewalType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaderboardSearch {

    private GoalZoneDto zone;
    private IanaTimeZone timeZone;
    private RenewalType renewalType = RenewalType.NEVER;
    @Builder.Default
    private List<LeaderboardSearchEntry> entries = new ArrayList<>();

}