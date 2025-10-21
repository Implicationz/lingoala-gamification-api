package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectiveDefinitionDto {

    private Long id;
    private ProgressValue worth = ProgressValue.valueOf(1);
    private GoalDefinitionDto parent;
    private GoalDefinitionDto child;
}