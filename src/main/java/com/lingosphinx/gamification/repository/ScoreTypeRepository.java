package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreTypeRepository extends JpaRepository<ScoreType, Long> {
    
    Optional<ScoreType> findByName(String name);
}
