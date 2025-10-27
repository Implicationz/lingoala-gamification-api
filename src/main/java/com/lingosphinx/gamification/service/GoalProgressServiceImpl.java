package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalProgress;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import com.lingosphinx.gamification.dto.GoalProgressDto;
import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.event.GoalProgressCreatedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.GoalProgressMapper;
import com.lingosphinx.gamification.repository.GoalProgressRepository;
import com.lingosphinx.gamification.repository.GoalRepository;
import com.lingosphinx.gamification.repository.ObjectiveDefinitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalProgressServiceImpl implements GoalProgressService {

    private final GoalRepository goalRepository;
    private final GoalProgressRepository goalProgressRepository;
    private final ObjectiveDefinitionRepository objectiveDefinitionRepository;
    private final GoalProgressMapper goalProgressMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void propagate(GoalProgress goalProgress) {
        var goal = goalProgress.getGoal();
        var definition = goal.getDefinition();
        var contestant = goal.getContestant();

        var matches = goalRepository.findObjectivesWithOptionalGoal(definition, contestant);
        for (var match : matches) {
            var parentGoal = Optional.ofNullable(match.goal()).orElseGet(() -> {
                var newGoal = Goal.builder()
                        .definition(match.objectiveDefinition().getParent())
                        .contestant(contestant)
                        .build();
                return goalRepository.save(newGoal);
            });
            objectivePropagation(parentGoal, match.objectiveDefinition(), goalProgress);
        }
    }

    public void objectivePropagation(Goal goal, ObjectiveDefinition objective, GoalProgress sourceProgress) {
        var progress = objective.propagate(goal, sourceProgress);
        this.progress(progress);
    }

    public GoalProgress progress(GoalProgress goalProgress) {
        var goal = goalProgress.getGoal();

        eventPublisher.publishEvent(new GoalProgressCreatedEvent(goalProgress));

        var wasComplete = goal.isComplete();
        goal.apply(goalProgress);
        var isComplete = goal.isComplete();

        var savedProgress = goalProgressRepository.save(goalProgress);
        log.info("Progress created successfully: id={}", savedProgress.getId());

        if (!wasComplete && isComplete) {
            eventPublisher.publishEvent(new GoalCompletedEvent(goal, savedProgress));
        }
        return savedProgress;
    }

    @Override
    public GoalProgressDto create(GoalProgressDto goalProgressDto) {
        var progress = goalProgressMapper.toEntity(goalProgressDto);
        var goal = goalRepository.findById(progress.getGoal().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        progress.setGoal(goal);
        var savedProgress = this.progress(progress);
        return goalProgressMapper.toDto(savedProgress);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalProgressDto readById(Long id) {
        var progress = goalProgressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
        log.info("Progress read successfully: id={}", id);
        return goalProgressMapper.toDto(progress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalProgressDto> readAll() {
        var result = goalProgressRepository.findAll().stream()
                .map(goalProgressMapper::toDto)
                .toList();
        log.info("All progresss read successfully, count={}", result.size());
        return result;
    }

    @Override
    public GoalProgressDto update(Long id, GoalProgressDto goalProgressDto) {
        var existingProgress = goalProgressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));

        goalProgressMapper.toEntityFromDto(goalProgressDto, existingProgress);
        log.info("Progress updated successfully: id={}", existingProgress.getId());
        return goalProgressMapper.toDto(existingProgress);
    }

    @Override
    public void delete(Long id) {
        goalProgressRepository.deleteById(id);
        log.info("Progress deleted successfully: id={}", id);
    }
}