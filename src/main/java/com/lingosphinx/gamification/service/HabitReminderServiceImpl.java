package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.HabitReminder;
import com.lingosphinx.gamification.domain.HabitReminderTrigger;
import com.lingosphinx.gamification.dto.HabitReminderDto;
import com.lingosphinx.gamification.event.RemindersCreatedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.HabitReminderMapper;
import com.lingosphinx.gamification.repository.HabitReminderRepository;
import com.lingosphinx.gamification.repository.HabitReminderTriggerRepository;
import com.lingosphinx.gamification.repository.HabitReminderTriggerSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitReminderServiceImpl implements HabitReminderService {

    private final HabitReminderRepository habitReminderRepository;
    private final HabitReminderTriggerRepository habitReminderTriggerRepository;
    private final HabitReminderMapper habitReminderMapper;
    private final ApplicationEventPublisher publisher;

    @Transactional
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

    @Transactional
    @Override
    public HabitReminderDto update(Long id, HabitReminderDto habitReminderDto) {
        var existingHabitReminder = habitReminderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HabitReminder not found"));

        habitReminderMapper.toEntityFromDto(habitReminderDto, existingHabitReminder);
        log.info("HabitReminder updated successfully: id={}", existingHabitReminder.getId());
        return habitReminderMapper.toDto(existingHabitReminder);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        habitReminderRepository.deleteById(id);
        log.info("HabitReminder deleted successfully: id={}", id);
    }

    public HabitReminder toReminder(HabitReminderTrigger habitReminderTrigger) {
        var habit = habitReminderTrigger.getHabit();
        var reminder = HabitReminder.builder()
                .habit(habit)
                .title("Streak Erinnerung")
                .body("Vergiss nicht, deinen Habit heute zu erledigen!")
                .build();
        return reminder;
    }

    @Transactional
    @Override
    public Page<HabitReminderTrigger> remind(Pageable pageable) {
        var spec = HabitReminderTriggerSpecifications.due()
                .and(HabitReminderTriggerSpecifications.noReminderExists());
        var page = habitReminderTriggerRepository.findAll(spec, pageable);
        var reminders = page.stream().map(t -> {
            var reminder = t.reminder();
            t.reset();
            log.info("HabitReminder saved: habitId={}", reminder.getHabit().getId());
            return reminder;
        }).toList();
        habitReminderRepository.saveAll(reminders);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                publisher.publishEvent(new RemindersCreatedEvent());
            }
        });
        return page;
    }

    @Transactional
    @Override
    public void deleteAll() {
        habitReminderRepository.deleteAll();
    };
}