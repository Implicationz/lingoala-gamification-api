package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalProgress;
import com.lingosphinx.gamification.domain.Objective;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import com.lingosphinx.gamification.event.ObjectiveActivatedEvent;
import com.lingosphinx.gamification.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ObjectiveServiceImpl implements ObjectiveService {

    private final GoalProgressService goalProgressService;
    private final ObjectiveDefinitionRepository objectiveDefinitionRepository;
    private final ObjectiveRepository objectiveRepository;
    private final GoalProgressRepository goalProgressRepository;
    private final GoalRepository goalRepository;
    private final ApplicationEventPublisher eventPublisher;

    protected Goal goalByObjective(ObjectiveDefinition objective, Goal child) {
        var goal = goalRepository.findByDefinitionAndContestant(objective.getParent(), child.getContestant())
                .orElseGet(() -> Goal.fromDefinition(objective.getParent(), child.getContestant()));
        return goal;
    }

    @Override
    public void activateParents(Goal goal) {
        log.info("Objective activation started for Goal={}", goal.getId());

        var definitions = objectiveDefinitionRepository
                .findAll(ObjectiveDefinitionSpecifications.parentWithoutObjectiveForContestant(goal));

        if (definitions.isEmpty()) {
            return;
        }

        var parentsToSave = definitions.stream()
                .map(d -> this.goalByObjective(d, goal))
                .collect(Collectors.toList());

        var savedParents = goalRepository.saveAll(parentsToSave);

        var objectives = IntStream.range(0, definitions.size())
                .mapToObj(i -> Objective.fromDefinition(definitions.get(i), savedParents.get(i), goal))
                .collect(Collectors.toList());

        objectiveRepository.saveAll(objectives);
        objectives.forEach(objective -> {
            eventPublisher.publishEvent(new ObjectiveActivatedEvent(objective));
        });
    }

    @Override
    public void propagate(GoalProgress goalProgress) {
        var goal = goalProgress.getGoal();
        var objectives = objectiveRepository.findAllByChild(goal);
        objectives.forEach(objective -> {
            this.propagate(objective, goalProgress);
        });
    }

    @Override
    public void propagate(Objective objective, GoalProgress goalProgress) {
        var progress = objective.propagate(goalProgress);
        var saved = goalProgressService.progress(progress);
        objective.setPropagation(saved);
    }
}
