package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"parent_id", "source_id"})
)
public class ObjectiveDefinition extends BaseEntity {

    @Builder.Default
    @Column(nullable = false)
    private Double worth = 1.0;

    @ManyToOne(optional = false)
    private GoalDefinition parent;

    @ManyToOne(optional = false)
    private GoalDefinition child;

    public GoalProgress propagate(Goal goal, GoalProgress sourceProgress) {
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

    public ProgressValue getWeightedTarget() {
        return this.child.getTarget().weighted(this.worth);
    }
}