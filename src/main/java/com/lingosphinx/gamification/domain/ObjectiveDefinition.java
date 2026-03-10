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
    uniqueConstraints = @UniqueConstraint(columnNames = {"parent_id", "child_id"})
)
public class ObjectiveDefinition extends BaseEntity {

    @Builder.Default
    @Column(nullable = false)
    private Double worth = 1.0;

    @ManyToOne(optional = false)
    private GoalDefinition parent;

    @ManyToOne(optional = false)
    private GoalDefinition child;

    public ProgressValue getWeightedTarget() {
        return this.child.getTarget().weighted(this.worth);
    }
}