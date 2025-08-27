package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.ProgressValue;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreProgressDto {

    private Long id;
    private ScoreDto score;
    private ScoreSessionDto session;
    private ProgressValue value;
}
