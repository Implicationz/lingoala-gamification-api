package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContestantExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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