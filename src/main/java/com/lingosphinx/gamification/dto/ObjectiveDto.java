package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.GoalProgress;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectiveDto {

    private ObjectiveDefinitionDto definition;
    private GoalDto parent;
    private GoalDto child;
    private GoalProgressDto propagation;

}