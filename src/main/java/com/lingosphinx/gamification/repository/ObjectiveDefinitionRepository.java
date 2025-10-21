package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ObjectiveDefinitionRepository extends JpaRepository<ObjectiveDefinition, Long>, JpaSpecificationExecutor<ObjectiveDefinition> {
    List<ObjectiveDefinition> findAllByChildId(Long id);

}
