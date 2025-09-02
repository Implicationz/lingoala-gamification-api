package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.Data;

@Data
public class GoalDefinitionRegistrationDto {
    private GoalDefinitionRegistrationDto parent;
    private String zone;
    private String type;
    private String name;
    private String reference;
    private ProgressValue worth = ProgressValue.valueOf(1);
    private ProgressValue target = ProgressValue.valueOf(1);
    private String image;
    private ExperienceValue experience = ExperienceValue.ZERO;
}