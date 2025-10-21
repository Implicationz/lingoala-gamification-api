package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.GoalDefinition;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class GoalDefinitionSpecifications {
    public static Specification<GoalDefinition> byTypeNameAndReference(String type, String reference) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("type").get("name"), type),
                cb.equal(root.get("reference"), reference)
        );
    }

    public static Specification<GoalDefinition> byTypeAndReferences(String type, List<String> references) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();
            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get("type").get("name"), type));
            }
            if (references != null && !references.isEmpty()) {
                predicate = cb.and(predicate, root.get("reference").in(references));
            }
            return predicate;
        };
    }

}