package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.ContestantExperience;
import com.lingosphinx.gamification.domain.GoalZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ContestantExperienceRepository extends JpaRepository<ContestantExperience, Long>, JpaSpecificationExecutor<ContestantExperience> {
    Optional<ContestantExperience> findByContestantAndZone(Contestant contestant, GoalZone zone);
}