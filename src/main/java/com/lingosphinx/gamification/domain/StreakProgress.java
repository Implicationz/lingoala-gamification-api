package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StreakProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "streak_id")
    private Streak streak;

    private Instant timestamp;
}
