package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.ExperienceProgress;
import org.springframework.context.ApplicationEvent;

public class ExperienceProgressCreatedEvent extends ApplicationEvent {
    public ExperienceProgressCreatedEvent(ExperienceProgress source) {
        super(source);
    }

    public ExperienceProgress getProgress() {
        return (ExperienceProgress) getSource();
    }
}
