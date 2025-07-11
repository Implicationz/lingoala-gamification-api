package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Goal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long>, JpaSpecificationExecutor<Goal> {
    @EntityGraph(attributePaths = {"parent", "definition", "children"})
    Optional<Goal> findById(Long id);

    @EntityGraph(attributePaths = {"definition"})
    List<Goal> findAll();

    @EntityGraph(attributePaths = {"parent", "definition", "children"})
    Optional<Goal> findOne(Specification spec);
}
