package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        indexes = {
                @Index(name = "idx_journey_entry_timestamp", columnList = "timestamp")
        }
)
public class JourneyEntry extends BaseEntity {

    @ManyToOne(optional = false)
    private Journey journey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JourneyEntryType type;

    @ManyToOne(optional = false)
    private JourneyItem item;

    @Builder.Default
    @Column(nullable = false)
    private Instant timestamp = Instant.now();

}