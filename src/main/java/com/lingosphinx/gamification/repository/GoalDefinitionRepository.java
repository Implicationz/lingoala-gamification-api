package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalDefinition;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalDefinitionRepository extends JpaRepository<GoalDefinition, Long> {

    @EntityGraph(attributePaths = {"parent", "children"})
    Optional<GoalDefinition> findById(Long id);

    List<GoalDefinition> findAll();
}
