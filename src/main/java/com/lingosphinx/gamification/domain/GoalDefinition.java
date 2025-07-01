package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"type", "reference"}),
        indexes = {
                @Index(name = "idx_goaldefinition_zone", columnList = "zone_id"),
                @Index(name = "idx_goaldefinition_type_reference", columnList = "type,reference")
        }
)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private GoalDefinition parent;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private GoalZone zone;

    private String name = "";
    private String type = "";
    private String reference = "";

    private int worth = 1;
    private ProgressValue target = ProgressValue.valueOf(1);

    private String image = "";

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoalDefinition> children = new ArrayList<>();

    public GoalDefinition findChildByType(String type) {
        return children.stream().filter(child -> Objects.equals(child.type, type)).findFirst().orElse(null);
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