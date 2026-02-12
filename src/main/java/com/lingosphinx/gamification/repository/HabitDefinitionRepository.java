package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.HabitDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface HabitDefinitionRepository extends JpaRepository<HabitDefinition, Long>, JpaSpecificationExecutor<HabitDefinition> {

    Optional<HabitDefinition> findByZone_NameAndName(String zone, String name);
    Optional<HabitDefinition> findByZone_NameAndType_Name(String zone, String name);
}
