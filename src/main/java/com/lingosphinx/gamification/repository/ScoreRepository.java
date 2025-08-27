package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScoreRepository extends JpaRepository<Score, Long>, JpaSpecificationExecutor<Score> {
}