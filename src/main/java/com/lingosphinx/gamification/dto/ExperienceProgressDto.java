package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ExperienceValue;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExperienceProgressDto {

    private Long id;
    private ContestantExperienceDto contestant;
    private ExperienceValue experience;

}