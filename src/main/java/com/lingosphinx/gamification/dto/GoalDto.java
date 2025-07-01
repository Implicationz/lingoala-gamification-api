package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class GoalDto {
    private Long id;
    private GoalDefinitionDto definition;
    private GoalDto parent;
    private String userId;
    private ProgressValue progress;
    private Instant lastRenewal;
    private Instant lastProgress;
    private List<GoalDto> children;
}