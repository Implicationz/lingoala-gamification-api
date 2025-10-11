package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.HabitReminder;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HabitReminderRepository extends JpaRepository<HabitReminder, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"habit", "habit.definition", "habit.streak", "habit.contestant"})
    List<HabitReminder> findBySentFalseAndTrialCountLessThan(int maxTrialCount);

    @Query(value = """
        SELECT hr.* FROM habit_reminder hr
        JOIN habit h ON hr.habit_id = h.id
        JOIN habit_definition d ON h.definition_id = d.id
        JOIN streak s ON h.streak_id = s.id
        JOIN contestant c ON h.contestant_id = c.id
        WHERE hr.sent = false AND hr.trial_count < :maxTrialCount
        FOR UPDATE SKIP LOCKED
        LIMIT :batchSize
        """,
        nativeQuery = true
    )
    List<HabitReminder> findLockUnsentReminders(int maxTrialCount, int batchSize);


}
