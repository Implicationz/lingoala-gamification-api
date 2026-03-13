package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.GoalProgress;
import com.lingosphinx.gamification.dto.GoalProgressDto;
import org.springframework.context.ApplicationEvent;

public class GoalProgressCreatedEvent extends ApplicationEvent {
    public GoalProgressCreatedEvent(GoalProgressDto source) {
        super(source);
    }

    public GoalProgressDto getProgress() {
        return (GoalProgressDto) getSource();
    }
}
