package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.HabitReminderTrigger;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface HabitReminderTriggerRepository extends JpaRepository<HabitReminderTrigger, Long>, JpaSpecificationExecutor<HabitReminderTrigger> {

    @EntityGraph(attributePaths = {"habit", "habit.definition", "habit.streak", "habit.contestant"})
    List<HabitReminderTrigger> findAll();

    @EntityGraph(attributePaths = {"habit", "habit.definition", "habit.streak", "habit.contestant"})
    List<HabitReminderTrigger> findAll(Specification spec);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"habit", "habit.definition", "habit.streak", "habit.contestant"})
    Page<HabitReminderTrigger> findAll(Specification<HabitReminderTrigger> spec, Pageable pageable);
}
