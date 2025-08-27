package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.domain.ScoreSnapshot;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreDto {

    private Long id;
    private ScoreDefinitionDto definition;
    private ContestantDto contestant;
    @Builder.Default
    private ProgressValue amount = ProgressValue.ZERO;
    @Builder.Default
    private Instant lastProgress = Instant.EPOCH;
    @Builder.Default
    private List<ScoreSnapshotDto> snapshots = new ArrayList<>();
}