package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalTypeRepository extends JpaRepository<GoalType, Long> {
    
    Optional<GoalType> findByName(String name);
}
