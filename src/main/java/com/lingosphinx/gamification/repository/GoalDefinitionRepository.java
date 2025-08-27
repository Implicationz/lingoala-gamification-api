package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalDefinition;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GoalDefinitionRepository extends JpaRepository<GoalDefinition, Long>, JpaSpecificationExecutor<GoalDefinition> {

    @EntityGraph(attributePaths = {"parent", "parent.type", "parent.zone", "children"})
    Optional<GoalDefinition> findById(Long id);

    List<GoalDefinition> findAll();

    @EntityGraph(attributePaths = {"parent", "children"})
    Optional<GoalDefinition> findOne(Specification spec);

    @EntityGraph(attributePaths = {"parent", "children"})
    Optional<GoalDefinition> findByZone_NameAndType_NameAndReference(String zone, String type, String reference);
}
