package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.Habit;
import org.springframework.context.ApplicationEvent;

public class HabitCompletedEvent extends ApplicationEvent {
    public HabitCompletedEvent(Habit source) {
        super(source);
    }

    public Habit getHabit() {
        return (Habit) getSource();
    }
}
