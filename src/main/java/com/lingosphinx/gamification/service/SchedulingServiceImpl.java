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
        var page = habitReminderService.remind(PageRequest.of(0, 500));
        var count = page.stream().count();
        while(page.hasNext()) {
            page = habitReminderService.remind(page.nextPageable());
            count += page.stream().count();
        }
        log.info("Finished habit reminder generation: {} reminders", count);
    }
}