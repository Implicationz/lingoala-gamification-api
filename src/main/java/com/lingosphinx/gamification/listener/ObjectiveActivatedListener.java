package com.lingosphinx.gamification.listener;


import com.lingosphinx.gamification.event.ObjectiveActivatedEvent;
import com.lingosphinx.gamification.service.ObjectiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ObjectiveActivatedListener {

    private final ObjectiveService objectiveService;

    @Async
    @TransactionalEventListener
    public void onObjectiveActivated(ObjectiveActivatedEvent event) {
        var objective = event.getObjective();
        objectiveService.activateParents(objective.getParent());
        var propagation = objective.propagation();
        objectiveService.propagate(propagation);
    }
}

