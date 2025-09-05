package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.client.NotificationClient;
import com.lingosphinx.gamification.domain.HabitReminder;
import com.lingosphinx.gamification.dto.NotificationDto;
import com.lingosphinx.gamification.repository.HabitReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitReminderNotificationServiceImpl implements HabitReminderNotificationService {

    private final HabitReminderRepository habitReminderRepository;
    private final NotificationClient notificationClient;

    protected NotificationDto toNotification(HabitReminder habitReminder) {
        var habit = habitReminder.getHabit();
        return NotificationDto.builder()
                .title("Habit Reminder")
                .message("Reminder for habit: " + habit.getDefinition().getName())
                .receiver(habit.getContestant().getUserId())
                .build();
    }

    @Transactional
    @Override
    public void sendPendingReminders() {
        var maxTrialCount = 3;
        var pendingReminders = habitReminderRepository.findBySentFalseAndTrialCountLessThan(maxTrialCount);
        for (var reminder : pendingReminders) {
            try {
                var notification = toNotification(reminder);
                notificationClient.create(notification);
                reminder.setSent(true);
                reminder.setSentAt(Instant.now());
            } catch (Exception ex) {
                log.warn("Reminder could not be sent: {}", reminder.getId(), ex);
            } finally {
                reminder.setTrialCount(reminder.getTrialCount() + 1);
                habitReminderRepository.save(reminder);
            }
        }
    }
}