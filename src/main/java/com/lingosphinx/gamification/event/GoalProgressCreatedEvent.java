package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.GoalProgress;
import org.springframework.context.ApplicationEvent;

public class GoalProgressCreatedEvent extends ApplicationEvent {
    public GoalProgressCreatedEvent(GoalProgress source) {
        super(source);
    }

    public GoalProgress getProgress() {
        return (GoalProgress) getSource();
    }
}
