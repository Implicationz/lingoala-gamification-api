package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Score score;

    @ManyToOne
    private ScoreSession session;

    private ProgressValue value;

    public ExperienceValue calculateExperience() {
        var value = this.value.getValue() * this.getScore().getDefinition().getExperience().getValue();
        return ExperienceValue.valueOf(value);
    }
}
