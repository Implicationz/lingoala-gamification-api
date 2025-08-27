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
    @Column(nullable = false)
    private Instant lastProgress = Instant.EPOCH;

    @Builder.Default
    @Column(nullable = false)
    private long duration = 0;

    @Builder.Default
    @Column(nullable = false)
    private long longestDuration = 0;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "streak", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StreakProgress> history;

    public void apply(StreakProgress progress) {
        this.duration += 1;
        this.longestDuration = Math.max(this.longestDuration, this.duration);
        this.lastProgress = progress.getTimestamp();
    }

    public void reset() {
        this.duration = 0;
    }
}
