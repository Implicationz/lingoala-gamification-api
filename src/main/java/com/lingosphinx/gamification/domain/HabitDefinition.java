package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"zone_id", "type_id"}),
        indexes = {
                @Index(name = "idx_habitdefinition_zone", columnList = "zone_id"),
                @Index(name = "idx_habitdefinition_zone_type", columnList = "zone_id,type_id")
        }
)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HabitDefinition extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private GoalZone zone;

    @ManyToOne
    @JoinColumn(nullable = false)
    private HabitType type;

    private String name = "";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RenewalType renewalType = RenewalType.NEVER;
    
    private String image = "";

    private ProgressValue target = ProgressValue.valueOf(1);

    @Builder.Default
    private ExperienceValue experience = ExperienceValue.ZERO;

}