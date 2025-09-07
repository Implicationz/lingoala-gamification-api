package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.dto.HabitActivationDto;
import com.lingosphinx.gamification.dto.HabitDto;
import com.lingosphinx.gamification.event.HabitCompletedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.HabitMapper;
import com.lingosphinx.gamification.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final HabitDefinitionRepository habitDefinitionRepository;
    private final HabitMapper habitMapper;

    private final StreakProgressRepository streakProgressRepository;
    private final StreakService streakService;
    private final ContestantService contestantService;
    private final ApplicationEventPublisher publisher;

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
        var contestant = contestantService.readCurrentContestant();
        habit.setContestant(contestant);
        var savedHabit = habitRepository.save(habit);
        log.info("Habit created successfully for user={}: id={}", contestant.getUserId(), savedHabit.getId());
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

        var history = streakProgressRepository.findAll(spec, Sort.by("timestamp"));
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
    public HabitDto readByZoneAndName(String zone, String name) {
        var contestant = contestantService.readCurrentContestant();
        var spec = HabitSpecifications.byZoneNameAndNameAndContestant(zone, name, contestant);
        var habit = habitRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found for zone=" + zone + " and name=" + name + " and contestant=" + contestant.getId()));
        log.info("Habit read by zone, name and contestant: zone={}, name={}, contestant={}, id={}", zone, name, contestant.getId(), habit.getId());
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

        var wasComplete = existingHabit.isComplete();
        existingHabit.setProgress(habitDto.getProgress());

        if(wasComplete && existingHabit.isComplete()) {
            publisher.publishEvent(new HabitCompletedEvent(existingHabit));
        }
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
    public void resetAll(IanaTimeZone ianaTimeZone) {
        var spec = HabitSpecifications.due(ianaTimeZone);
        var habits = habitRepository.findAll(spec);
        log.info("Habit reset started for {} habits", habits.size());
        habits.forEach(this::reset);
        log.info("Habit reset completed {} habits", habits.size());
    }

    @Override
    public HabitDto activate(HabitActivationDto habitActivation) {
        var contestant = contestantService.readCurrentContestant();
        var spec = HabitSpecifications.byZoneNameAndNameAndContestant(habitActivation.getZone(), habitActivation.getName(), contestant);
        var found = habitRepository.findOne(spec);
        var activated = found.orElseGet(() -> {
            var definition = habitDefinitionRepository.findByZone_NameAndName(habitActivation.getZone(), habitActivation.getName())
                    .orElseThrow(() -> new IllegalArgumentException("HabitDefinition is invalid."));
            var habit = Habit.fromDefinition(definition, contestant);
            var savedGoal = habitRepository.save(habit);
            log.info("Goal activated successfully: id={}", savedGoal.getId());
            return savedGoal;
        });
        return this.habitMapper.toDto(activated);
    }
}