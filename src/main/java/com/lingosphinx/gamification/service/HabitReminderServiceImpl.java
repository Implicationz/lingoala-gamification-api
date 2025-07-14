package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.HabitReminder;
import com.lingosphinx.gamification.dto.HabitReminderDto;
import com.lingosphinx.gamification.mapper.HabitReminderMapper;
import com.lingosphinx.gamification.repository.HabitReminderRepository;
import com.lingosphinx.gamification.repository.HabitRepository;
import com.lingosphinx.gamification.repository.HabitSpecifications;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitReminderServiceImpl implements HabitReminderService {

    private final HabitReminderRepository habitReminderRepository;
    private final HabitRepository habitRepository;
    private final HabitReminderMapper habitReminderMapper;

    @Override
    public HabitReminderDto create(HabitReminderDto habitReminderDto) {
        var habitReminder = habitReminderMapper.toEntity(habitReminderDto);
        var savedHabitReminder = habitReminderRepository.save(habitReminder);
        log.info("HabitReminder created successfully: id={}", savedHabitReminder.getId());
        return habitReminderMapper.toDto(savedHabitReminder);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitReminderDto readById(Long id) {
        var habitReminder = habitReminderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HabitReminder not found"));
        log.info("HabitReminder read successfully: id={}", id);
        return habitReminderMapper.toDto(habitReminder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitReminderDto> readAll() {
        var result = habitReminderRepository.findAll().stream()
                .map(habitReminderMapper::toDto)
                .toList();
        log.info("All habitReminders read successfully, count={}", result.size());
        return result;
    }

    @Override
    public HabitReminderDto update(Long id, HabitReminderDto habitReminderDto) {
        var existingHabitReminder = habitReminderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HabitReminder not found"));

        habitReminderMapper.toEntityFromDto(habitReminderDto, existingHabitReminder);
        log.info("HabitReminder updated successfully: id={}", existingHabitReminder.getId());
        return habitReminderMapper.toDto(existingHabitReminder);
    }

    @Override
    public void delete(Long id) {
        habitReminderRepository.deleteById(id);
        log.info("HabitReminder deleted successfully: id={}", id);
    }

    public void remind(Habit habit) {
        var fcmToken = "";
        if (fcmToken != null && !fcmToken.isBlank()) {
            var reminder = HabitReminder.builder()
                    .habitId(habit.getId())
                    .fcmToken(fcmToken)
                    .title("Streak Erinnerung")
                    .body("Vergiss nicht, deinen Habit heute zu erledigen!")
                    .build();
            habitReminderRepository.save(reminder);
            log.info("HabitReminder gespeichert: habitId={}", habit.getId());
        }
    }
    @Override
    public void remindAll() {
        var spec = HabitSpecifications.due();
        var habits = habitRepository.findAll(spec);
        habits.forEach(this::remind);
    }
}