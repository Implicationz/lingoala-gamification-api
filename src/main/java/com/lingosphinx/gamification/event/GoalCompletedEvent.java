package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalProgress;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class GoalCompletedEvent extends ApplicationEvent {

    private GoalProgress progress;

    public GoalCompletedEvent(Goal source, GoalProgress goalProgress) {
        super(source);
        this.progress = goalProgress;
    }

    public Goal getGoal() {
        return (Goal) getSource();
    }
}
