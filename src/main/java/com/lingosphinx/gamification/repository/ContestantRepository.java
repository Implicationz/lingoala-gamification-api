package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContestantRepository extends JpaRepository<Contestant, Long> {
    @EntityGraph(attributePaths = {"experiences", "experiences.zone"})
    Optional<Contestant> findByUserId(UUID userId);

    @Override
    @EntityGraph(attributePaths = {"experiences", "experiences.zone"})
    Optional<Contestant> findById(Long id);
}