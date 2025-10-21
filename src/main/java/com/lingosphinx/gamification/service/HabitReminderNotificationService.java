package com.lingosphinx.gamification.service;

import org.springframework.transaction.annotation.Transactional;

public interface HabitReminderNotificationService {
    @Transactional
    boolean sendPendingReminders(int batchSize);

    void sendAllPendingReminders();
}
