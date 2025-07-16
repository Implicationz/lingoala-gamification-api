package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.HabitReminder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RemindersCreatedEvent {
    private final List<HabitReminder> reminders;
}