package com.lingosphinx.gamification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreProgress extends BaseEntity {

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
