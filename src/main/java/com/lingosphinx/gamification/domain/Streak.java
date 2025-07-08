package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Embeddable
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Streak {

    @Enumerated(EnumType.STRING)
    private RenewalType renewalType = RenewalType.NEVER;
    @Builder.Default
    private Instant lastRenewal = Instant.EPOCH;
    private long duration;

    public void advance() {
    }
}
