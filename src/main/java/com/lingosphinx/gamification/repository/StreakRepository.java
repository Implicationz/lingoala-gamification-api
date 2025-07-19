package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StreakRepository extends JpaRepository<Streak, Long>, JpaSpecificationExecutor<Streak> {
}