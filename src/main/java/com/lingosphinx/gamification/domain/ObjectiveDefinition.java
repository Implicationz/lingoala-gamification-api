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
    private ProgressValue worth = ProgressValue.valueOf(1);

    @ManyToOne(optional = false)
    private GoalDefinition parent;

    @ManyToOne(optional = false)
    private GoalDefinition child;
}