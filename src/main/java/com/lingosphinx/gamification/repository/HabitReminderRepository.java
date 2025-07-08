package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.HabitReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HabitReminderRepository extends JpaRepository<HabitReminder, Long> {}
