package com.lingosphinx.gamification.dto;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreSessionTypeDto {
    private Long id;
    private String name = "";
}