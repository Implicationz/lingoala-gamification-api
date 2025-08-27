package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.domain.RenewalType;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HabitDefinitionDto {

    private Long id;

    private GoalZoneDto zone;

    private String name = "";

    @Builder.Default
    private RenewalType renewalType = RenewalType.NEVER;
    
    private String image = "";

    @Builder.Default
    private ProgressValue target = ProgressValue.valueOf(1);

    @Builder.Default
    private ExperienceValue experience = ExperienceValue.ZERO;

}