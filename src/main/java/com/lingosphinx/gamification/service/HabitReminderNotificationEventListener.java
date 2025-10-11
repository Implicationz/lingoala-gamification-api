package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.event.RemindersCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitReminderNotificationEventListener {

    private final HabitReminderNotificationService notificationService;

    @Async
    @EventListener
    public void onRemindersCreated(RemindersCreatedEvent event) {
        notificationService.sendAllPendingReminders();
    }
}