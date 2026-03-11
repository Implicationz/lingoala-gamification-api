package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.Objective;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import com.lingosphinx.gamification.dto.ObjectiveDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ObjectiveMapper;
import com.lingosphinx.gamification.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ObjectiveServiceImpl implements ObjectiveService {

    private final ObjectiveDefinitionRepository objectiveDefinitionRepository;
    private final ObjectiveRepository objectiveRepository;
    private final GoalProgressRepository goalProgressRepository;
    private final GoalRepository goalRepository;

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

        var objectivesWithPropagations = objectives.stream()
                .filter((o) -> o.getPropagation() != null)
                .collect(Collectors.toList());
        var propagations = objectivesWithPropagations.stream()
                .map(Objective::getPropagation).collect(Collectors.toList());
        var savedPropagations = goalProgressRepository.saveAll(propagations);
        for (var i = 0; i < savedPropagations.size(); ++i) {
            var propagation = savedPropagations.get(i);
            var objective = objectivesWithPropagations.get(i);
            objective.setPropagation(propagation);
        }
        objectiveRepository.saveAll(objectives);
        parentsToSave.forEach(this::activateParents);
    }
}
