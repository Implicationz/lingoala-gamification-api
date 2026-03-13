package com.lingosphinx.gamification.listener;


import com.lingosphinx.gamification.event.GoalProgressCreatedEvent;
import com.lingosphinx.gamification.mapper.GoalProgressMapper;
import com.lingosphinx.gamification.service.GoalProgressService;
import com.lingosphinx.gamification.service.ObjectiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoalProgressCreatedListener {

    private final ObjectiveService objectiveService;
    private final GoalProgressMapper goalProgressMapper;

    @Async
    @TransactionalEventListener
    public void onGoalProgressCreated(GoalProgressCreatedEvent event) {
        var goalProgress = goalProgressMapper.toEntity(event.getProgress());
        objectiveService.propagate(goalProgress);
    }
}

