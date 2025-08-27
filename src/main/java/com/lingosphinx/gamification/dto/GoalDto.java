package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalDto {
    private Long id;
    private GoalDefinitionDto definition;
    private GoalDto parent;
    private ContestantDto contestant;
    private ProgressValue progress;
    private Instant lastProgress;
    private List<GoalDto> children;
}