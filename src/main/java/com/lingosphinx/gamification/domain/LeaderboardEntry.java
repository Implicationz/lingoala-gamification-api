package com.lingosphinx.gamification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class LeaderboardEntry extends BaseEntity {

    @ManyToOne
    private Leaderboard leaderboard;

    @ManyToOne
    private Contestant contestant;

    @Builder.Default
    private ExperienceValue progress = ExperienceValue.ZERO;

}
