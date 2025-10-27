package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.domain.RenewalType;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaderboardDto {

    private Long id;
    private GoalZone zone;
    private IanaTimeZone timeZone;
    private RenewalType renewalType = RenewalType.NEVER;

}