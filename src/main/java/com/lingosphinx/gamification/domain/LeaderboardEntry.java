package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Leaderboard leaderboard;

    @ManyToOne
    private Contestant contestant;


    private ExperienceValue progress = ExperienceValue.ZERO;

}
