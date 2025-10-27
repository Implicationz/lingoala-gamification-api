package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.domain.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyRepository extends JpaRepository<Journey, Long> {
    Optional<Journey> findByZoneAndContestant(GoalZone goalZone, Contestant contestant);
}