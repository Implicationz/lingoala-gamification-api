package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"zone_id", "type_id", "reference"}),
        indexes = {
                @Index(name = "idx_goaldefinition_zone", columnList = "zone_id"),
                @Index(name = "idx_goaldefinition_type", columnList = "type_id"),
                @Index(name = "idx_goaldefinition_type_reference", columnList = "type_id,reference")
        }
)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private GoalDefinition parent;

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
    private ProgressValue worth = ProgressValue.valueOf(1);
    @Builder.Default
    @Column(nullable = false)
    private ProgressValue target = ProgressValue.valueOf(1);
    private String image = "";

    @Builder.Default
    @Column(nullable = false)
    private ExperienceValue experience = ExperienceValue.ZERO;

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoalDefinition> children = new ArrayList<>();

    public GoalDefinition findChildByType(String type) {
        return children.stream().filter(child -> Objects.equals(child.getType().getName(), type)).findFirst().orElse(null);
    }

    public GoalDefinition findChildByReference(String reference) {
        return children.stream().filter(child -> Objects.equals(child.reference, reference)).findFirst().orElse(null);
    }

    public GoalDefinition findChildByDefinition(GoalDefinition other) {
        return children.stream().filter(child ->
                Objects.equals(child.type, other.type) &&
                        Objects.equals(child.reference, other.reference)
        ).findFirst().orElse(null);
    }
}