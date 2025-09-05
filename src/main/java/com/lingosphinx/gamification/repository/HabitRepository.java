package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.Habit;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long>, JpaSpecificationExecutor<Habit> {

    @EntityGraph(attributePaths = {"definition", "streak", "contestant"})
    Optional<Habit> findById(Long id);

    @EntityGraph(attributePaths = {"definition", "streak", "contestant"})
    List<Habit> findAll();

    @EntityGraph(attributePaths = {"definition", "streak", "contestant"})
    List<Habit> findAll(Specification spec);
    
    @EntityGraph(attributePaths = {"definition", "streak", "contestant"})
    Optional<Habit> findOne(Specification spec);
}
