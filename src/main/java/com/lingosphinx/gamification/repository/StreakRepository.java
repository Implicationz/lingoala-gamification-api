package com.lingosphinx.gamification.repository;


import com.lingosphinx.gamification.domain.Streak;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StreakRepository extends JpaRepository<Streak, Long> {
}
