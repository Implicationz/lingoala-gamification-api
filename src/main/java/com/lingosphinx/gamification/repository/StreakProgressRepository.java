package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.StreakProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StreakProgressRepository extends JpaRepository<StreakProgress, Long>, JpaSpecificationExecutor<StreakProgress> {
}