package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalProgress;
import com.lingosphinx.gamification.dto.GoalProgressDto;
import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.event.GoalProgressCreatedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.GoalProgressMapper;
import com.lingosphinx.gamification.repository.GoalProgressRepository;
import com.lingosphinx.gamification.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalProgressServiceImpl implements GoalProgressService {

    private final GoalRepository goalRepository;
    private final GoalProgressRepository goalProgressRepository;
    private final GoalProgressMapper goalProgressMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Async
    @EventListener
    public void handleGoalCompleted(GoalCompletedEvent event) {
        var goal = event.getGoal();
        var definition = goal.getDefinition();
        var parent = definition.getParent();
        if(parent == null) {
            return;
        }
        var contestant = goal.getContestant();
        var linkedGoal = goalRepository.findByDefinitionAndContestant(parent, contestant).orElseGet(() -> {
            var newGoal = Goal.fromDefinition(parent);
            newGoal.setContestant(contestant);
            return goalRepository.save(newGoal);
        });
        var sourceProgress = event.getProgress();
        var progress = GoalProgress.builder()
                .goal(linkedGoal)
                .startedAt(sourceProgress.getStartedAt())
                .finishedAt(sourceProgress.getFinishedAt())
                .value(definition.getWorth())
                .build();
        this.progress(progress);
    }

    public GoalProgress progress(GoalProgress goalProgress) {
        var goal = goalProgress.getGoal();
        var wasComplete = goal.isComplete();
        goal.apply(goalProgress);
        var isComplete = goal.isComplete();

        var savedProgress = goalProgressRepository.save(goalProgress);
        log.info("Progress created successfully: id={}", savedProgress.getId());

        eventPublisher.publishEvent(new GoalProgressCreatedEvent(savedProgress));

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