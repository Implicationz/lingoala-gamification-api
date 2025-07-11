package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RenewalType renewalType = RenewalType.NEVER;

    @Builder.Default
    @Column(nullable = false)
    private Instant lastRenewal = Instant.EPOCH;

    @Builder.Default
    @Column(nullable = false)
    private long duration = 0;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "streak", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StreakProgress> history;

    public void advance() {
    }
}
