package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private GoalZone zone;

    private String name = "";

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ScoreType type;

    private String image = "";

    @Builder.Default
    private ExperienceValue experience = ExperienceValue.ZERO;
}