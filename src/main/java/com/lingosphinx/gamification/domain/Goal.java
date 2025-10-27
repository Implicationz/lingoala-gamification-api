package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"definition_id", "contestant_id"}),
        indexes = {
                @Index(name = "idx_goal_contestant_id", columnList = "contestant_id")
        }
)
public class Goal extends BaseEntity {

    @ManyToOne(optional = false)
    private GoalDefinition definition;

    @ManyToOne(optional = false)
    private Contestant contestant;

    @Builder.Default
    private ProgressValue progress = ProgressValue.valueOf(0);

    @Builder.Default
    private Instant lastProgress = Instant.EPOCH;

    @Builder.Default
    @Transient
    private List<Objective> objectives = new ArrayList<>();

    public static Goal fromDefinition(GoalDefinition definition) {
        return Goal.builder()
                .definition(definition)
                .progress(ProgressValue.ZERO)
                .lastProgress(Instant.EPOCH)
                .objectives(new ArrayList<>())
                .build();
    }

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

    public GoalZone getZone() {
        return definition.getZone();
    }

    public ProgressValue getTarget() {
        return definition.getTarget();
    }

    public void setTarget(ProgressValue value) {
        definition.setTarget(value);
    }

    public long resting() {
        return definition.getTarget().getValue() - progress.getValue();
    }

    public boolean isComplete() {
        return resting() <= 0;
    }


    public void apply(GoalProgress goalProgress) {
        var value = goalProgress.getValue();
        if(this.progress.isGreaterOrEqual(value)) {
            return;
        }
        this.progress = value;
        this.lastProgress = Instant.now();
    }

    public String getImage() {
        return definition.getImage();
    }
}