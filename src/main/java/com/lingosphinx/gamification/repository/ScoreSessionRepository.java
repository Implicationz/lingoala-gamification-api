package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.ScoreSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScoreSessionRepository extends JpaRepository<ScoreSession, Long>, JpaSpecificationExecutor<ScoreSession> {
}