package com.lingosphinx.gamification.dto;

import lombok.*;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalTypeDto {
    private Long id;
    private String name = "";
}