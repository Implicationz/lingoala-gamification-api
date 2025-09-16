package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.HabitReminder;
import com.lingosphinx.gamification.dto.HabitReminderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HabitReminderService {
    HabitReminderDto create(HabitReminderDto habitReminder);
    HabitReminderDto readById(Long id);
    List<HabitReminderDto> readAll();
    HabitReminderDto update(Long id, HabitReminderDto habitReminder);
    void delete(Long id);

    List<HabitReminder> remind(Pageable pageable);
    void deleteAll();
}
