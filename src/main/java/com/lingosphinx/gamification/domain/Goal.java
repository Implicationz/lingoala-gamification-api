package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"definition_id", "user_id"}),
        indexes = {
                @Index(name = "idx_goal_user_id", columnList = "user_id")
        }
)
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "definition_id")
    private GoalDefinition definition;

    @ManyToOne(optional = true)
    private Goal parent;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Builder.Default
    private ProgressValue progress = ProgressValue.valueOf(0);

    @Builder.Default
    private Instant lastProgress = Instant.EPOCH;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> children = new ArrayList<>();

    public void reset() {
        setProgress(ProgressValue.ZERO);
    }

    public double getPercentage() {
        if (definition.getTarget() != null && definition.getTarget().getValue() != 0) {
            return (progress.getValue() / (double) definition.getTarget().getValue()) * 100;
        }
        return 0;
    }

    public GoalType getType() {
        return definition.getType();
    }

    public String getName() {
        return definition.getName();
    }

    public String getReference() {
        return definition.getReference();
    }

    public ProgressValue getTarget() {
        return definition.getTarget();
    }

    public void setTarget(ProgressValue value) {
        definition.setTarget(value);
    }

    public boolean advance(int byCount) {
        return advanceTo(new ProgressValue(this.progress.getValue() + byCount));
    }

    public boolean advanceTo(ProgressValue toCount) {
        boolean wasComplete = isComplete();
        this.progress = toCount;
        boolean isComplete = isComplete();
        this.lastProgress = Instant.now();
        boolean streakUpdate = !wasComplete && isComplete;
        return streakUpdate;
    }

    public long resting() {
        return definition.getTarget().getValue() - progress.getValue();
    }

    public boolean isComplete() {
        return resting() <= 0;
    }

    public void linkDefinition(GoalDefinition definition) {
        this.definition = definition;
        if (definition.getChildren() != null) {
            for (GoalDefinition child : definition.getChildren()) {
                Goal found = findChildByDefinition(child);
                if (found != null) {
                    found.linkDefinition(child);
                }
            }
        }
    }

    public Goal findChildByType(String type) {
        for (Goal child : children) {
            if (Objects.equals(child.getDefinition().getType(), type)) {
                return child;
            }
        }
        return null;
    }

    public Goal findChildByReference(String reference) {
        for (Goal child : children) {
            if (Objects.equals(child.getDefinition().getReference(), reference)) {
                return child;
            }
        }
        return null;
    }

    public Goal findChildByDefinition(GoalDefinition definition) {
        for (Goal child : children) {
            if (Objects.equals(child.getDefinition().getType(), definition.getType()) &&
                    Objects.equals(child.getDefinition().getReference(), definition.getReference())) {
                return child;
            }
        }
        return null;
    }

    public boolean isChildComplete(String gnodeReference) {
        Goal gnode = findChildByReference(gnodeReference);
        return gnode != null && gnode.isComplete();
    }

    public void apply(Progress progress) {
        this.progress = progress.getValue();
    }
}