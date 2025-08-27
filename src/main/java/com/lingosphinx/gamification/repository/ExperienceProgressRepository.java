package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.ExperienceProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceProgressRepository extends JpaRepository<ExperienceProgress, Long> {
}