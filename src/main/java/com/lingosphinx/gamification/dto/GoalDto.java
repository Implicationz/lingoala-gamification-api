package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class GoalDto {
    private Long id;
    private GoalDefinitionDto definition;
    private GoalDto parent;
    private UUID userId;
    private ProgressValue progress;
    private Instant lastProgress;
    private List<GoalDto> children;
}