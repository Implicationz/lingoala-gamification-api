package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.function.UnaryOperator;

@BatchSize(size=50)
@Entity
@Table(
        indexes = {
                @Index(name = "idx_habitremindertrigger_nextdue", columnList = "nextDue")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HabitReminderTrigger extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Habit habit;
    private LocalTime time;
    private Instant nextDue;

    public HabitReminder reminder() {
        var habit = this.getHabit();
        var reminder = HabitReminder.builder()
                .habit(habit)
                .title("Streak Erinnerung")
                .body("Vergiss nicht, deinen Habit heute zu erledigen!")
                .build();
        return reminder;
    }
    
    public void reset() {
        var contestant = this.habit.getContestant();
        var renewalType = this.habit.getDefinition().getRenewalType();
        var zoneId = contestant.getTimeZone().zoneId();
        var now = Instant.now().atZone(zoneId);

        this.nextDue = switch (renewalType) {
            case DAILY -> calculateNextDue(now, 1, ChronoUnit.DAYS, t -> t);
            case WEEKLY -> calculateNextDue(now.with(DayOfWeek.SUNDAY), 1, ChronoUnit.WEEKS, t -> t);
            case MONTHLY -> calculateNextDue(
                    now.withDayOfMonth(now.toLocalDate().lengthOfMonth()),
                    1, ChronoUnit.MONTHS,
                    t -> t.withDayOfMonth(t.toLocalDate().lengthOfMonth())
            );
            case YEARLY -> calculateNextDue(
                    now.withDayOfYear(now.toLocalDate().lengthOfYear()),
                    1, ChronoUnit.YEARS,
                    t -> t.withDayOfYear(t.toLocalDate().lengthOfYear())
            );
            default -> this.nextDue;
        };
    }

    private Instant calculateNextDue(ZonedDateTime base,
                                    int amountToAdd,
                                    TemporalUnit unit,
                                    UnaryOperator<ZonedDateTime> adjuster)
    {
        var targetTime = base.withHour(this.time.getHour())
                .withMinute(this.time.getMinute())
                .withSecond(this.time.getSecond())
                .withNano(0);
        var now = ZonedDateTime.now(targetTime.getZone());
        if (now.isAfter(targetTime)) {
            var nextPeriod = adjuster.apply(targetTime.plus(amountToAdd, unit));
            return nextPeriod.toInstant();
        } else {
            return targetTime.toInstant();
        }
    }
}
