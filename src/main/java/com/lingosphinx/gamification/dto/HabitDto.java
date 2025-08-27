package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HabitDto {

    private Long id;
    private HabitDefinitionDto definition;
    private ContestantDto contestant;
    @Builder.Default
    private StreakDto streak = new StreakDto();
    @Builder.Default
    private ProgressValue progress = ProgressValue.ZERO;
}
