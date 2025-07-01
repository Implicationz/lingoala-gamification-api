package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.RenewalType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StreakDto {

    private Long id;
    @Builder.Default
    private RenewalType renewalType = RenewalType.NEVER;
    private long duration;
}
