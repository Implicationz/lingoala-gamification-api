package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.ScoreDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ScoreDefinitionRepository extends JpaRepository<ScoreDefinition, Long>, JpaSpecificationExecutor<ScoreDefinition> {
    Optional<ScoreDefinition> findByZone_NameAndType_Name(String zone, String type);
}
