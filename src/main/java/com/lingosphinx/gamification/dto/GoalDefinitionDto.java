package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.Data;
import java.util.List;

@Data
public class GoalDefinitionDto {
    private Long id;
    private GoalDefinitionDto parent;
    private GoalZoneDto zone;
    private GoalTypeDto type;
    private String name;
    private String reference;
    private int worth;
    private ProgressValue target;
    private String image;
    private List<GoalDefinitionDto> children;
}