package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import org.springframework.data.jpa.domain.Specification;

public class GoalSpecifications {
    public static Specification<Goal> byTypeNameAndReference(String type, String reference) {
        return (root, query, cb) -> {
            var goalDefinition = root.get("definition");
            return cb.and(
                    cb.equal(goalDefinition.get("type").get("name"), type),
                    cb.equal(goalDefinition.get("reference"), reference)
            );
        };
    }

    public static Specification<Goal> byZoneNameAndTypeName(String zone, String type) {
        return (root, query, cb) -> {
            var goalDefinition = root.get("definition");
            return cb.and(
                    cb.equal(goalDefinition.get("zone").get("name"), zone),
                    cb.equal(goalDefinition.get("type").get("name"), type)
            );
        };
    }

    public static Specification<Goal> byZoneTypeNameAndReferenceAndContestant(String zone, String type, String reference, Contestant contestant) {
        return (root, query, cb) -> {
            var goalDefinition = root.get("definition");
            return cb.and(cb.equal(root.get("contestant"), contestant),
                cb.equal(goalDefinition.get("zone").get("name"), zone),
                cb.equal(goalDefinition.get("type").get("name"), type),
                cb.equal(goalDefinition.get("reference"), reference)
            );
        };
    }
}