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
    private ProgressValue worth;
    private ProgressValue target;
    private String image;
    private ExperienceValue experience;
}