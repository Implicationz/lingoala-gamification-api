package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.Streak;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long>, JpaSpecificationExecutor<Habit> {

    @EntityGraph(attributePaths = {"goal", "goal.definition", "streak"})
    Optional<Habit> findById(Long id);

    @EntityGraph(attributePaths = {"goal", "goal.definition", "streak"})
    List<Habit> findAll();
    
    @EntityGraph(attributePaths = {"goal", "goal.definition", "streak"})
    Optional<Habit> findOne(Specification spec);
}
