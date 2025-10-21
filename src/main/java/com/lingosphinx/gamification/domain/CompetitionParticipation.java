package com.lingosphinx.gamification.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompetitionParticipation extends BaseEntity {

    @ManyToOne(optional = false)
    private Contestant contestant;

    @ManyToOne(optional = false)
    private GoalZone zone;

    private ExperienceValue experience;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "contestant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceProgress> history;

    public void apply(ExperienceProgress progress) {
        this.experience = progress.getExperience();
    }
}