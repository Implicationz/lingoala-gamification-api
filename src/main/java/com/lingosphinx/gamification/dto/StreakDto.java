package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.RenewalType;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StreakDto {

    private Long id;
    @Builder.Default
    private RenewalType renewalType = RenewalType.NEVER;
    @Builder.Default
    private Instant lastRenewal = Instant.EPOCH;
    @Builder.Default
    private long duration = 0;
    @Builder.Default
    private List<StreakProgressDto> history = new ArrayList<>();
}
