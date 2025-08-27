package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ScoreProgress;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreSessionDto {

    private Long id;
    private ScoreSessionTypeDto type;
    private ContestantDto contestant;

    @Builder.Default
    private Instant startedAt = Instant.EPOCH;
    @Builder.Default
    private Instant finishedAt = Instant.now();

    @Builder.Default
    private List<ScoreProgress> events = new ArrayList<>();
}