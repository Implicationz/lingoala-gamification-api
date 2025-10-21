package com.lingosphinx.gamification.event;


import com.lingosphinx.gamification.service.GoalServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoalActivationListener {

    private final GoalServiceImpl goalService;

    @Async
    @TransactionalEventListener
    public void onGoalActivated(GoalActivatedEvent event) {
        log.info("Goal propagation started for Definition={}, Contestant={}",
                event.getDefinition().getId(), event.getContestant().getId());
        goalService.activateParents(event.getDefinition(), event.getContestant());
    }
}

