package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreDefinitionDto {

    private Long id;
    private GoalZoneDto zone;
    private ScoreTypeDto type;
    private String name = "";
    private String image = "";
    @Builder.Default
    private ExperienceValue experience = ExperienceValue.ZERO;
}