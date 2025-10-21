package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreSnapshot extends BaseEntity {

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