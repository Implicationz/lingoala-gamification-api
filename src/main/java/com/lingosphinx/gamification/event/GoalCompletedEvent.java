package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.Goal;
import org.springframework.context.ApplicationEvent;

public class GoalCompletedEvent extends ApplicationEvent {
    public GoalCompletedEvent(Goal source) {
        super(source);
    }

    public Goal getGoal() {
        return (Goal) getSource();
    }
}
