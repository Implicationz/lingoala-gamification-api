package com.lingosphinx.gamification.listener;


import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.event.HabitCompletedEvent;
import com.lingosphinx.gamification.event.ScoreProgressCreatedEvent;
import com.lingosphinx.gamification.service.ExperienceProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExperienceListener {

    private final ExperienceProgressService experienceProgressService;

    @Async
    @EventListener
    @Transactional
    public void handleGoalCompleted(GoalCompletedEvent event) {
        var goal = event.getGoal();
        var goalDefinition = goal.getDefinition();
        var zone = goalDefinition.getZone();
        experienceProgressService.progress(goal.getContestant(), zone, goalDefinition.getExperience());
    }

    @Async
    @EventListener
    @Transactional
    protected void handleHabitCompletedEvent(HabitCompletedEvent event) {
        var habit = event.getHabit();
        var habitDefinition = habit.getDefinition();
        var zone = habitDefinition.getZone();
        experienceProgressService.progress(habit.getContestant(), zone, habitDefinition.getExperience());
    }

    @Async
    @EventListener
    @Transactional
    protected void handleScoreProgressCreatedEvent(ScoreProgressCreatedEvent event) {
        var scoreProgress = event.getProgress();
        var score = scoreProgress.getScore();
        var scoreDefinition = score.getDefinition();
        var zone = scoreDefinition.getZone();
        experienceProgressService.progress(score.getContestant(), zone, scoreProgress.calculateExperience());
    }
}

