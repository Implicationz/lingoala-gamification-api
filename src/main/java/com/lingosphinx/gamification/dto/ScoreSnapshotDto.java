package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.domain.RenewalType;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreSnapshotDto {

    private Long id;
    private ScoreDto score;
    private RenewalType renewalType;
    @Builder.Default
    private ProgressValue amount = ProgressValue.ZERO;
}