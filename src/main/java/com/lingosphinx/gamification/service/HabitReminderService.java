package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.HabitReminderDto;

import java.util.List;

public interface HabitReminderService {
    HabitReminderDto create(HabitReminderDto habitReminder);
    HabitReminderDto readById(Long id);
    List<HabitReminderDto> readAll();
    HabitReminderDto update(Long id, HabitReminderDto habitReminder);
    void delete(Long id);
    void remindAll();
}
