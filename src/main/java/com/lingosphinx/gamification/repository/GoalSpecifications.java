package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

    public static Specification<Goal> byContestant(Contestant contestant) {
        return (root, query, cb) -> cb.equal(root.get("contestant"), contestant);
    }

    public static Specification<Goal> byParentGoalDefinition(GoalDefinition parentGoalDefinition) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var objective = subquery.from(ObjectiveDefinition.class);
            subquery.select(objective.get("child").get("id"))
                    .where(cb.equal(objective.get("parent"), parentGoalDefinition));
            return root.get("definition").get("id").in(subquery);
        };
    }

    public static Specification<Goal> childrenOf(List<GoalDefinition> parentDefinitions) {
        return (root, query, cb) -> {
            var objectives = root.join("definition").join("objectives");
            return cb.and(
                    cb.equal(objectives.get("child"), root.get("definition")),
                    objectives.get("parent").in(parentDefinitions)
            );
        };
    }

    public static Specification<Goal> withDefinition(List<GoalDefinition> definitions, Contestant contestant) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("definition"), definitions),
                cb.equal(root.get("contestant"), contestant)
        );
    }

    public static Specification<Goal> byReferences(List<String> references) {
        return (root, query, cb) -> root.get("definition").get("reference").in(references);
    }

    public static Specification<Goal> isCompleted() {
        return (root, query, cb) -> {
            var diff = cb.diff(root.get("progress").get("value"), root.get("definition").get("target").get("value"));
            return cb.ge(diff, 0);
        };
    }

    public static Specification<Goal> isNotCompleted() {
        return (root, query, cb) -> {
            var diff = cb.diff(root.get("progress").get("value"), root.get("definition").get("target").get("value"));
            return cb.lt(diff, 0);
        };
    }
}
