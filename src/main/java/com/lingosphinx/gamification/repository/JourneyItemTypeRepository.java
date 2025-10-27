package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.JourneyItemCategory;
import com.lingosphinx.gamification.domain.JourneyItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyItemTypeRepository extends JpaRepository<JourneyItemType, Long> {
    Optional<JourneyItemType> findByCategoryAndName(JourneyItemCategory category, String name);
}
