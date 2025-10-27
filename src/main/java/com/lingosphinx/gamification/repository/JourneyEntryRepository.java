package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.JourneyEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourneyEntryRepository extends JpaRepository<JourneyEntry, Long> {
}