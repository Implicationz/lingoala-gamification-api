package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"zone_id", "contestant_id"}),
        indexes = {
                @Index(name = "idx_journey_zone_contestant", columnList = "zone_id,contestant_id")
        }
)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Journey extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private GoalZone zone;

    @ManyToOne
    private Contestant contestant;

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JourneyEntry> entries = new ArrayList<>();

}