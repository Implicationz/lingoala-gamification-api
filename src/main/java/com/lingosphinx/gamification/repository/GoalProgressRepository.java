package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoalProgressRepository extends JpaRepository<GoalProgress, Long>, JpaSpecificationExecutor<GoalProgress> {
}