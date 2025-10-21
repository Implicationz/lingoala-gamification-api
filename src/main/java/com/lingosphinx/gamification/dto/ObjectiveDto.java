package com.lingosphinx.gamification.dto;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectiveDto {

    private GoalDto parent;
    private GoalDto child;
}