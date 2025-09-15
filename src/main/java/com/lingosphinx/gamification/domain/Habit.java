package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

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

    @BatchSize(size = 50)
    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitReminderTrigger> triggers;

    @Builder.Default
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private Streak streak = new Streak();

    private ProgressValue progress = ProgressValue.ZERO;

    @Builder.Default
    @Column(nullable = false)
    private Instant lastProgress = Instant.EPOCH;

    public static Habit fromDefinition(HabitDefinition definition, Contestant contestant) {
        var habit = Habit.builder()
                .definition(definition)
                .contestant(contestant)
                .streak(new Streak())
                .progress(ProgressValue.valueOf(1))
                .build();

        habit.setTriggers(Stream.of(
            HabitReminderTrigger.builder()
                    .habit(habit)
                    .time(LocalTime.MIDNIGHT.minusHours(2))
                    .build(),
            HabitReminderTrigger.builder()
                    .habit(habit)
                    .time(LocalTime.MIDNIGHT.minusHours(7))
                    .build()
        ).toList());
        habit.resetReminderTriggers();
        return habit;
    }

    private void resetReminderTriggers() {
        this.triggers.forEach(HabitReminderTrigger::reset);
    }

    public void reset() {
        var isComplete = this.isComplete();
        this.progress = ProgressValue.ZERO;
        if(isComplete) {
            return;
        }
        this.streak.reset();
    }

    public boolean isComplete() {
        return this.progress.getValue() >= this.definition.getTarget().getValue();
    }

    public void apply(ProgressValue progress) {
        this.setProgress(progress);
        this.lastProgress = Instant.now();
    }
}
