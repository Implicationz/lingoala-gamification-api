package com.lingosphinx.gamification.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.List;

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Streak extends BaseEntity {

    @Builder.Default
    @Column(nullable = false)
    private Instant lastProgress = Instant.EPOCH;

    @Builder.Default
    @Column(nullable = false)
    private Instant lastReset = Instant.EPOCH;

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
        this.lastReset = Instant.now();
    }
}
