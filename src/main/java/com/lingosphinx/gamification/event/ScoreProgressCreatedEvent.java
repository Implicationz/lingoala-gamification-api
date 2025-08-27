package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.ScoreProgress;
import org.springframework.context.ApplicationEvent;

public class ScoreProgressCreatedEvent extends ApplicationEvent {
    public ScoreProgressCreatedEvent(ScoreProgress source) {
        super(source);
    }

    public ScoreProgress getProgress() {
        return (ScoreProgress) getSource();
    }
}
