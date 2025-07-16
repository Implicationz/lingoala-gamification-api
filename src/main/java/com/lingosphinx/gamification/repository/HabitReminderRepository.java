package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.HabitReminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitReminderRepository extends JpaRepository<HabitReminder, Long> {}
