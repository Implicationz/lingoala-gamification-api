package com.lingosphinx.gamification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreDefinition extends BaseEntity {

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