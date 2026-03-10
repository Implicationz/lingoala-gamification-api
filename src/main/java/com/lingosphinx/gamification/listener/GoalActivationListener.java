package com.lingosphinx.gamification.listener;


import com.lingosphinx.gamification.event.GoalActivatedEvent;
import com.lingosphinx.gamification.service.GoalService;
import com.lingosphinx.gamification.service.ObjectiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoalActivationListener {

    private final ObjectiveService objectiveService;

    @Async
    @TransactionalEventListener
    public void onGoalActivated(GoalActivatedEvent event) {
        var goal = event.getGoal();
        objectiveService.activateParents(goal);
        log.info("Objective activation finished for Goal={}", goal.getId());
    }
}

