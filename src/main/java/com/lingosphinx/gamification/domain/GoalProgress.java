package com.lingosphinx.gamification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalProgress extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    private ProgressValue value;

    @Builder.Default
    private Instant startedAt = Instant.EPOCH;
    @Builder.Default
    private Instant finishedAt = Instant.now();

    public ProgressValue delta(double weight) {
        return this.value.weighted(weight).difference(goal.getProgress().weighted(weight));
    }
}
