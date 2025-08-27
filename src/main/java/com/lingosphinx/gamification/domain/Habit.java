package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private HabitDefinition definition;

    @ManyToOne
    private Contestant contestant;

    @Builder.Default
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private Streak streak = new Streak();

    private ProgressValue progress = ProgressValue.ZERO;

    public static Habit fromDefinition(HabitDefinition definition) {
        return Habit.builder()
                .definition(definition)
                .streak(new Streak())
                .progress(ProgressValue.valueOf(1))
                .build();
    }

    public void reset() {
        this.progress = ProgressValue.ZERO;
        this.streak.reset();
    }

    public boolean isComplete() {
        return this.progress.getValue() >= this.definition.getTarget().getValue();
    }
}
