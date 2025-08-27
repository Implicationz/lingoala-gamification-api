package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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