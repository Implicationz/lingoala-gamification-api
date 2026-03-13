package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.GoalProgress;
import com.lingosphinx.gamification.domain.Objective;
import org.springframework.context.ApplicationEvent;

public class ObjectiveActivatedEvent extends ApplicationEvent {
    public ObjectiveActivatedEvent(Objective source) {
        super(source);
    }

    public Objective getObjective() {
        return (Objective) getSource();
    }
}
