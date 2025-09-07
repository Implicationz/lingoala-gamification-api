package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.HabitReminderTrigger;
import com.lingosphinx.gamification.dto.HabitReminderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HabitReminderService {
    HabitReminderDto create(HabitReminderDto habitReminder);
    HabitReminderDto readById(Long id);
    List<HabitReminderDto> readAll();
    HabitReminderDto update(Long id, HabitReminderDto habitReminder);
    void delete(Long id);

    Page<HabitReminderTrigger> remind(Pageable pageable);
    void deleteAll();
}
