package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalZoneRepository extends JpaRepository<GoalZone, Long> {
    
    Optional<GoalZone> findByName(String name);
}
