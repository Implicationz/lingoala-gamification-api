package com.lingosphinx.gamification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@BatchSize(size = 30)
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"definition_id", "parent_id", "child_id"})
)
public class Objective extends BaseEntity {

    @ManyToOne(optional = false)
    private ObjectiveDefinition definition;

    @ManyToOne(optional = false)
    private Goal parent;

    @ManyToOne(optional = false)
    private Goal child;

    @ManyToOne(optional = true)
    private GoalProgress propagation;

    public static Objective fromDefinition(ObjectiveDefinition definition, Goal parent, Goal child) {
        var objective = Objective.builder()
                .definition(definition)
                .parent(parent)
                .child(child)
                .build();
        return objective;
    }

    public GoalProgress propagation() {
        var progress = this.child.getProgress();
        var propagation = GoalProgress.of(this.parent, progress.weighted(this.getWorth()));
        return propagation;
    }

    public double getWorth() {
        return this.getDefinition().getWorth();
    }

    public GoalProgress propagate(GoalProgress sourceProgress) {
        var goal = this.parent;
        var delta = sourceProgress.delta(this.getWorth());
        var value = goal.getProgress().add(delta);
        var progress = GoalProgress.builder()
                .goal(goal)
                .startedAt(sourceProgress.getStartedAt())
                .finishedAt(sourceProgress.getFinishedAt())
                .value(value)
                .build();
        return progress;
    }
}