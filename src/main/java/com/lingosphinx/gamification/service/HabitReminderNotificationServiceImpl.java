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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitReminderNotificationServiceImpl implements HabitReminderNotificationService {

    private final HabitReminderRepository habitReminderRepository;
    private final NotificationClient notificationClient;

    protected NotificationDto toNotification(HabitReminder habitReminder) {
        var habit = habitReminder.getHabit();
        var name = habit.getDefinition().getName();
        var zone = habit.getDefinition().getZone();
        var message = String.format("Reminder for habit: %s (%s)", name, zone.getName());
        return NotificationDto.builder()
                .title("Habit Reminder")
                .message(message)
                .receiver(habit.getContestant().getUserId())
                .build();
    }

    public void processReminder(HabitReminder reminder) {
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

    @Transactional
    public boolean sendPendingReminders(int batchSize) {
        var maxTrialCount = 3;
        var batch = habitReminderRepository.findLockUnsentReminders(maxTrialCount, batchSize);
        for (var reminder : batch) {
            processReminder(reminder);
        }
        return !batch.isEmpty();
    }

    @Override
    public void sendAllPendingReminders() {
        var batchSize = 20;
        while (sendPendingReminders(batchSize));
    }

}