package com.lingosphinx.gamification.dto;

import lombok.*;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HabitTypeDto {
    private Long id;
    private String name = "";
}