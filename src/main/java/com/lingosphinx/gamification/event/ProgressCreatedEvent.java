package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.Progress;
import org.springframework.context.ApplicationEvent;

public class ProgressCreatedEvent extends ApplicationEvent {
    public ProgressCreatedEvent(Progress source) {
        super(source);
    }

    public Progress getProgress() {
        return (Progress) getSource();
    }
}
