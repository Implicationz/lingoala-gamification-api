package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import lombok.*;

import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContestantExperienceDto {

    private Long id;
    private ContestantDto contestant;
    private GoalZoneDto zone;
    private ExperienceValue experience;
    private List<ExperienceProgressDto> history;
}