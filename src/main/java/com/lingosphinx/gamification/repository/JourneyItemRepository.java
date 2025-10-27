package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.JourneyItem;
import com.lingosphinx.gamification.domain.JourneyItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyItemRepository extends JpaRepository<JourneyItem, Long> {
    Optional<JourneyItem> findByTypeAndReference(JourneyItemType type, String reference);
}
