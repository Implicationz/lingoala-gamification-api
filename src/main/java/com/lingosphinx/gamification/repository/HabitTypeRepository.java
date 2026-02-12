package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.HabitType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitTypeRepository extends JpaRepository<HabitType, Long> {
    
    Optional<HabitType> findByName(String name);
}
