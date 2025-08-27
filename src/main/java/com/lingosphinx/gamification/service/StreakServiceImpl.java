package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Streak;
import com.lingosphinx.gamification.domain.StreakProgress;
import com.lingosphinx.gamification.dto.StreakDto;
import com.lingosphinx.gamification.event.HabitCompletedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.StreakMapper;
import com.lingosphinx.gamification.repository.StreakProgressRepository;
import com.lingosphinx.gamification.repository.StreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StreakServiceImpl implements StreakService {

    private final StreakRepository streakRepository;
    private final StreakMapper streakMapper;
    private final StreakProgressRepository streakProgressRepository;

    @EventListener
    @Transactional
    protected void handleHabitCompletedEvent(HabitCompletedEvent event) {
        var habit = event.getHabit();
        var streak = habit.getStreak();
        this.progress(streak);
    }

    @Override
    public StreakDto create(StreakDto streakDto) {
        var streak = streakMapper.toEntity(streakDto);
        var savedStreak = streakRepository.save(streak);
        log.info("Streak created successfully: id={}", savedStreak.getId());
        return streakMapper.toDto(savedStreak);
    }

    @Override
    @Transactional(readOnly = true)
    public StreakDto readById(Long id) {
        var streak = streakRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Streak not found"));
        return this.streakMapper.toDto(streak);
    }


    @Override
    @Transactional(readOnly = true)
    public List<StreakDto> readAll() {
        var result = streakRepository.findAll().stream()
                .map(streakMapper::toDto)
                .toList();
        log.info("All streaks read successfully, count={}", result.size());
        return result;
    }

    @Override
    public StreakDto update(Long id, StreakDto streakDto) {
        var existingStreak = streakRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Streak not found"));

        streakMapper.toEntityFromDto(streakDto, existingStreak);
        log.info("Streak updated successfully: id={}", existingStreak.getId());
        return streakMapper.toDto(existingStreak);
    }

    @Override
    public void delete(Long id) {
        streakRepository.deleteById(id);
        log.info("Streak deleted successfully: id={}", id);
    }

    @Override
    public void progress(Streak streak) {
        var now = Instant.now();
        var progress = StreakProgress.builder()
                .streak(streak)
                .timestamp(now)
                .build();
        streak.apply(progress);
        this.streakProgressRepository.save(progress);
        log.info("Streak progressed successfully: id={}", streak.getId());
    }
}