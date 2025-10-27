package com.lingosphinx.gamification.listener;


import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.service.JourneyEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JourneyListener {

    private final JourneyEntryService journeyEntryService;

    @EventListener
    public void handleGoalCompleted(GoalCompletedEvent event) {
        journeyEntryService.handle(event);
    }
}

