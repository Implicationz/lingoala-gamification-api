package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.ScoreSessionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreSessionTypeRepository extends JpaRepository<ScoreSessionType, Long> {
    
    Optional<ScoreSessionType> findByName(String name);
}
