package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.Streak;
import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HabitDto {
    private Long id;
    private GoalDto goal;
    private StreakDto streak;
}
