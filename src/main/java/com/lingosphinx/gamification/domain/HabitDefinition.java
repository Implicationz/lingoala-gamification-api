package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"zone_id", "name"}),
        indexes = {
                @Index(name = "idx_habitdefinition_zone", columnList = "zone_id"),
                @Index(name = "idx_habitdefinition_zone_name", columnList = "zone_id,name")
        }
)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HabitDefinition extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private GoalZone zone;

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