package com.lingosphinx.gamification.listener;


import com.lingosphinx.gamification.event.ExperienceProgressCreatedEvent;
import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.service.GoalProgressService;
import com.lingosphinx.gamification.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaderboardListener {

    private final LeaderboardService leaderboardService;

    @EventListener
    public void handleExperienceProgressCreated(ExperienceProgressCreatedEvent event) {

        leaderboardService.progress(event.getProgress());
    }
}

