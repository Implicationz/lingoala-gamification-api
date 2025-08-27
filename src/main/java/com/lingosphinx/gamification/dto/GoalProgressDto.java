package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.*;

import java.time.Instant;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalProgressDto {

    private Long id;
    private GoalDto goal;
    private ProgressValue value;
    @Builder.Default
    private Instant startedAt = Instant.EPOCH;
    @Builder.Default
    private Instant finishedAt = Instant.now();
}
