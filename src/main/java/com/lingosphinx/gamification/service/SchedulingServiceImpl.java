package com.lingosphinx.gamification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingServiceImpl implements SchedulingService {

    private final HabitReminderService habitReminderService;

    @Override
    public void remindAll() {
        log.info("Started habit reminder generation");
        habitReminderService.deleteAll();
        var count = 0;
        while(true) {
            var reminders = habitReminderService.remind(PageRequest.of(0, 500));
            if (reminders.isEmpty()) {
                break;
            }
            count += reminders.size();
        }
        log.info("Finished habit reminder generation: {} reminders", count);
    }
}