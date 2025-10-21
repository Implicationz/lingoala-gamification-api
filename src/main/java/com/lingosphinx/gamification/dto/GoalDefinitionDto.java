package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.*;

import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalDefinitionDto {
    private Long id;
    private GoalZoneDto zone;
    private GoalTypeDto type;
    private String name;
    private String reference;
    @Builder.Default
    private ProgressValue target = ProgressValue.valueOf(1);
    @Builder.Default
    private ExperienceValue experience = ExperienceValue.ZERO;
    private String image;
    private List<ObjectiveDefinitionDto> objectives;
}