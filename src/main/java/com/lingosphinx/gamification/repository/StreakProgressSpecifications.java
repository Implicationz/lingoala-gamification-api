package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.StreakProgress;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class StreakProgressSpecifications {

    public static Specification<StreakProgress> byStreakId(Long streakId) {
        return (root, query, cb) -> cb.equal(root.get("streak").get("id"), streakId);
    }

    public static Specification<StreakProgress> timestampAfter(Instant timestamp) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("timestamp"), timestamp);
    }
}
