package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.event.GoalCompletedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.HabitMapper;
import com.lingosphinx.gamification.repository.HabitRepository;
import com.lingosphinx.gamification.repository.HabitSpecifications;
import com.lingosphinx.gamification.repository.StreakProgressRepository;
import com.lingosphinx.gamification.repository.StreakProgressSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final HabitMapper habitMapper;

    private final StreakProgressRepository progressRepository;
    private final UserService userService;

    @EventListener
    public void onGoalCompleted(GoalCompletedEvent event) {
        var goal = event.getGoal();
        var habitOpt = habitRepository.findByGoal(goal);
        if (habitOpt.isEmpty()) {
            return;
        }
        var habit = habitOpt.get();
        habit.getStreak().advance();
    }

    @Override
    public HabitDto create(HabitDto habitDto) {
        var habit = habitMapper.toEntity(habitDto);
        var savedHabit = habitRepository.save(habit);
        log.info("Habit created successfully: id={}", savedHabit.getId());
        return habitMapper.toDto(savedHabit);
    }

    @Override
    public HabitDto createByCurrentUser(HabitDto habitDto) {
        var habit = habitMapper.toEntity(habitDto);
        var userId = userService.getCurrentUserId();
        habit.getGoal().setUserId(userId);
        var savedHabit = habitRepository.save(habit);
        log.info("Habit created successfully for user={}: id={}", userId, savedHabit.getId());
        return habitMapper.toDto(savedHabit);
    }

    @Transactional(readOnly = true)
    protected void fetchStreakHistory(Habit habit) {
        var streak = habit.getStreak();
        var tenDaysAgoStart = LocalDate.now()
                .minusDays(9)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();

        var spec = StreakProgressSpecifications.byStreakId(streak.getId())
                .and(StreakProgressSpecifications.timestampAfter(tenDaysAgoStart));

        var history = progressRepository.findAll(spec, Sort.by("timestamp"));
        streak.setHistory(history);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitDto readById(Long id) {
        var habit = habitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found"));
        fetchStreakHistory(habit);
        return this.habitMapper.toDto(habit);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitDto readByTypeNameAndReference(String type, String reference) {
        var spec = HabitSpecifications.byTypeNameAndReference(type, reference);
        var habit = habitRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found for type=" + type + " and reference=" + reference));
        log.info("Habit read by type and reference: type={}, reference={}, id={}", type, reference, habit.getId());
        fetchStreakHistory(habit);
        return habitMapper.toDto(habit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitDto> readAll() {
        var result = habitRepository.findAll().stream()
                .map(habitMapper::toDto)
                .toList();
        log.info("All habits read successfully, count={}", result.size());
        return result;
    }

    @Override
    public HabitDto update(Long id, HabitDto habitDto) {
        var existingHabit = habitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found"));

        habitMapper.toEntityFromDto(habitDto, existingHabit);
        log.info("Habit updated successfully: id={}", existingHabit.getId());
        return habitMapper.toDto(existingHabit);
    }

    @Override
    public void delete(Long id) {
        habitRepository.deleteById(id);
        log.info("Habit deleted successfully: id={}", id);
    }

    public void reset(Habit habit) {
        habit.reset();
        log.info("Habit streak reset: id={}", habit.getId());
    }

    @Override
    public void resetAll() {
        var spec = HabitSpecifications.due();
        var habits = habitRepository.findAll(spec);
        log.info("Habit reset started for {} habits", habits.size());
        habits.forEach(this::reset);
        log.info("Habit reset completed {} habits", habits.size());
    }
}