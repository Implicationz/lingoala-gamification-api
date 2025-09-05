package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.HabitReminder;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface HabitReminderRepository extends JpaRepository<HabitReminder, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"habit", "habit.definition", "habit.streak", "habit.contestant"})
    List<HabitReminder> findBySentFalseAndTrialCountLessThan(int maxTrialCount);
}
