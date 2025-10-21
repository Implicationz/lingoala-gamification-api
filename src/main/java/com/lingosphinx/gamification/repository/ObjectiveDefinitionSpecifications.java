package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import org.springframework.data.jpa.domain.Specification;

public class ObjectiveDefinitionSpecifications {

    public static Specification<ObjectiveDefinition> parentWithoutGoalForContestant(GoalDefinition childDefinition, Contestant contestant) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var goalRoot = subquery.from(Goal.class);
            subquery.select(cb.literal(1L))
                    .where(
                            cb.equal(goalRoot.get("definition"), root.join("parent")),
                            cb.equal(goalRoot.get("contestant"), contestant)
                    );

            return cb.and(
                    cb.equal(root.get("child"), childDefinition),
                    cb.not(cb.exists(subquery))
            );
        };
    }
}
