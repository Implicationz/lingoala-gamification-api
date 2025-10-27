package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.domain.Leaderboard;
import com.lingosphinx.gamification.domain.RenewalType;
import com.lingosphinx.gamification.dto.GoalZoneDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long>, JpaSpecificationExecutor<Leaderboard> {

    Optional<Leaderboard> findByZoneAndRenewalType(GoalZone zone, RenewalType renewalType);

    List<Leaderboard> findAllByZone(GoalZone goalZone);
}
