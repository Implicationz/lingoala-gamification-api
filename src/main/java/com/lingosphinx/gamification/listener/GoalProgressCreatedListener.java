package com.lingosphinx.gamification.listener;


import com.lingosphinx.gamification.event.GoalProgressCreatedEvent;
import com.lingosphinx.gamification.service.GoalProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoalProgressCreatedListener {

    private final GoalProgressService goalProgressService;

    @EventListener
    public void onGoalProgressCreated(GoalProgressCreatedEvent event) {
        var goalProgress = event.getProgress();
        goalProgressService.propagate(goalProgress);
    }
}

