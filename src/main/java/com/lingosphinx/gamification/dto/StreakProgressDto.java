package com.lingosphinx.gamification.dto;

import lombok.*;

import java.time.Instant;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StreakProgressDto {

    private Long id;
    private StreakDto streak;
    private Instant timestamp;
}
