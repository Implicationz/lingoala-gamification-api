package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Score;
import org.springframework.data.jpa.domain.Specification;

public class ScoreSpecifications {

    public static Specification<Score> byZoneTypeAndContestant(String zone, String type, Contestant contestant) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("definition").get("zone").get("name"), zone),
                cb.equal(root.get("definition").get("type").get("name"), type),
                cb.equal(root.get("contestant"), contestant)
        );
    }
}