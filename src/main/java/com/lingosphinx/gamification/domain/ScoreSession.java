package com.lingosphinx.gamification.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreSession extends BaseEntity {

    @ManyToOne(optional = false)
    private ScoreSessionType type;

    @ManyToOne(optional = false)
    private Contestant contestant;

    @Builder.Default
    private Instant startedAt = Instant.EPOCH;
    @Builder.Default
    private Instant finishedAt = Instant.now();

    @Builder.Default
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScoreProgress> events = new ArrayList<>();
}