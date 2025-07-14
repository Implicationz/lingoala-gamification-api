package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ProgressDto;
import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.event.ProgressCreatedEvent;
import com.lingosphinx.gamification.mapper.ProgressMapper;
import com.lingosphinx.gamification.repository.GoalRepository;
import com.lingosphinx.gamification.repository.ProgressRepository;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProgressServiceImpl implements ProgressService {

    private final GoalRepository goalRepository;
    private final ProgressRepository progressRepository;
    private final ProgressMapper progressMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public ProgressDto create(ProgressDto progressDto) {
        var progress = progressMapper.toEntity(progressDto);
        var goal = goalRepository.findById(progress.getGoal().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        var wasComplete = goal.isComplete();
        goal.apply(progress);
        var isComplete = goal.isComplete();

        var savedProgress = progressRepository.save(progress);
        log.info("Progress created successfully: id={}", savedProgress.getId());

        eventPublisher.publishEvent(new ProgressCreatedEvent(savedProgress));

        if (!wasComplete && isComplete) {
            eventPublisher.publishEvent(new GoalCompletedEvent(goal));
        }

        return progressMapper.toDto(savedProgress);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgressDto readById(Long id) {
        var progress = progressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));
        log.info("Progress read successfully: id={}", id);
        return progressMapper.toDto(progress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgressDto> readAll() {
        var result = progressRepository.findAll().stream()
                .map(progressMapper::toDto)
                .toList();
        log.info("All progresss read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ProgressDto update(Long id, ProgressDto progressDto) {
        var existingProgress = progressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progress not found"));

        progressMapper.toEntityFromDto(progressDto, existingProgress);
        log.info("Progress updated successfully: id={}", existingProgress.getId());
        return progressMapper.toDto(existingProgress);
    }

    @Override
    public void delete(Long id) {
        progressRepository.deleteById(id);
        log.info("Progress deleted successfully: id={}", id);
    }
}