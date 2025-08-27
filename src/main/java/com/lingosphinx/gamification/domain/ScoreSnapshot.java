package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    private Score score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RenewalType renewalType;

    @Builder.Default
    private ProgressValue amount = ProgressValue.ZERO;

    public void regenerate() {
        setAmount(ProgressValue.ZERO);
    }
}