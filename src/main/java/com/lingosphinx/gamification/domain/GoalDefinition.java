package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"zone_id", "type_id", "reference"}),
        indexes = {
                @Index(name = "idx_goaldefinition_zone", columnList = "zone_id"),
                @Index(name = "idx_goaldefinition_type", columnList = "type_id"),
                @Index(name = "idx_goaldefinition_type_reference", columnList = "type_id,reference")
        }
)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalDefinition extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private GoalZone zone;

    private String name = "";

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private GoalType type;

    private String reference = "";
    @Builder.Default
    @Column(nullable = false)
    private ProgressValue target = ProgressValue.valueOf(1);
    private String image = "";

    @Builder.Default
    @Column(nullable = false)
    private ExperienceValue experience = ExperienceValue.ZERO;

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObjectiveDefinition> objectives = new ArrayList<>();

    public ProgressValue totalObjectiveTarget() {
        return this.objectives.stream()
                .map(ObjectiveDefinition::getWeightedTarget)
                .reduce(new ProgressValue(0), ProgressValue::add);
    }
    public void recalculate() {
        this.target = this.totalObjectiveTarget();
    }

}