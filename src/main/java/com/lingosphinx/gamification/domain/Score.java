package com.lingosphinx.gamification.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@BatchSize(size = 5)
@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Score extends BaseEntity {

    @ManyToOne(optional = false)
    private ScoreDefinition definition;

    @ManyToOne(optional = false)
    private Contestant contestant;

    @Builder.Default
    private ProgressValue amount = ProgressValue.ZERO;

    @Builder.Default
    private Instant lastProgress = Instant.EPOCH;

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "score", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScoreSnapshot> snapshots = new ArrayList<>();

    public void reset() {
        setAmount(ProgressValue.ZERO);
    }

    public void apply(ScoreProgress progress) {
        this.amount = progress.getValue();
        this.lastProgress = Instant.now();
    }

    public static Score fromDefinition(ScoreDefinition definition) {
        var score = Score.builder()
                .definition(definition)
                .build();

        for (var renewalType : RenewalType.values()) {
            if(renewalType.equals(RenewalType.NEVER)) {
                continue;
            }
            var snapshot = ScoreSnapshot.builder()
                    .score(score)
                    .renewalType(renewalType)
                    .build();
            score.getSnapshots().add(snapshot);
        }

        return score;
    }
}