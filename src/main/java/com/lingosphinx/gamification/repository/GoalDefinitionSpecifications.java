package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalDefinition;
import org.springframework.data.jpa.domain.Specification;

public class GoalDefinitionSpecifications {
    public static Specification<GoalDefinition> byTypeNameAndReference(String type, String reference) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("type").get("name"), type),
                cb.equal(root.get("reference"), reference)
        );
    }
}