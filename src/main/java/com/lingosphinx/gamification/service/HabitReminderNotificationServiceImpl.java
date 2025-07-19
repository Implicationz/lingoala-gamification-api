package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.client.NotificationClient;
import com.lingosphinx.gamification.domain.HabitReminder;
import com.lingosphinx.gamification.dto.NotificationDto;
import com.lingosphinx.gamification.event.RemindersCreatedEvent;
import com.lingosphinx.gamification.repository.HabitReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitReminderNotificationServiceImpl {

    private final HabitReminderRepository habitReminderRepository;
    private final NotificationClient notificationClient;


    protected NotificationDto toNotification(HabitReminder habitReminder) {
        var habit = habitReminder.getHabit();
        return NotificationDto.builder()
                .title("Habit Reminder")
                .message("Reminder for habit: " + habit.getName())
                .receiver(habit.getGoal().getUserId())
                .build();
    }

    @Async
    @EventListener
    public void onRemindersCreated(RemindersCreatedEvent event) {
        var reminders = event.getReminders();
        for (var reminder : reminders) {
            try {
                log.info("Sending reminder: {}", reminder.getId());
                var notification = toNotification(reminder);
                notificationClient.create(notification);
                reminder.setSent(true);
                reminder.setSentAt(Instant.now());
                reminder.setTrialCount(reminder.getTrialCount() + 1);
            } catch (Exception ex) {
                reminder.setTrialCount(reminder.getTrialCount() + 1);
                log.warn("Reminder could not be sent: {}", reminder.getId(), ex);
            }
            habitReminderRepository.save(reminder);
        }
    }
}