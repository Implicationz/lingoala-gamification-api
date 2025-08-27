package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import com.lingosphinx.gamification.domain.RenewalType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HabitDefinitionRegistrationDto {
    private String zone;
    private String name;
    @Builder.Default
    private RenewalType renewalType = RenewalType.NEVER;
    private ProgressValue target;
    private String image;
}