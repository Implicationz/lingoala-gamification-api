package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"zone_id", "timeZone", "renewalType"}
    )
)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Leaderboard extends BaseEntity {

    @ManyToOne(optional = false)
    private GoalZone zone;

    private IanaTimeZone timeZone;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RenewalType renewalType = RenewalType.NEVER;

}