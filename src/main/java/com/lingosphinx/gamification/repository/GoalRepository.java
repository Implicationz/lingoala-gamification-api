package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long>, JpaSpecificationExecutor<Goal> {
    @EntityGraph(attributePaths = {"parent", "definition", "definition.zone", "definition.type", "children", "contestant"})
    Optional<Goal> findById(Long id);

    @EntityGraph(attributePaths = {"definition", "definition.zone"})
    List<Goal> findAll();

    @EntityGraph(attributePaths = {"parent", "definition", "definition.zone", "definition.type", "children", "contestant"})
    Optional<Goal> findOne(Specification spec);

    @EntityGraph(attributePaths = {"parent", "definition", "definition.zone", "definition.type"})
    Optional<Goal> findByDefinitionAndContestant(GoalDefinition definition, Contestant contestant);
}
