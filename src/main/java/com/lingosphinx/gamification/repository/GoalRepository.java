package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long>, JpaSpecificationExecutor<Goal> {
    @Override
    @EntityGraph(attributePaths = {"definition", "definition.zone", "definition.type", "contestant"})
    Optional<Goal> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"definition", "definition.zone"})
    List<Goal> findAll();

    @Override
    @EntityGraph(attributePaths = {"definition", "definition.zone", "definition.type", "contestant"})
    Optional<Goal> findOne(Specification spec);

    @EntityGraph(attributePaths = {"definition", "definition.zone", "definition.type"})
    Optional<Goal> findByDefinitionAndContestant(GoalDefinition definition, Contestant contestant);

    @Query("""
    select new com.lingosphinx.gamification.repository.GoalObjectiveMatch(g, o)
    from Goal g
    join g.contestant c
    join g.definition d
    join d.objectives o
    where c = :contestant AND o.child = :childDefinition
    """)
    List<GoalObjectiveMatch> findGoalsWithChildDefinition(@Param("contestant") Contestant contestant,
                                                          @Param("childDefinition") GoalDefinition childDefinition);

    @Query("""
    select new com.lingosphinx.gamification.repository.GoalObjectiveMatch(g, o)
    from GoalDefinition d
    join d.objectives o
    left join Goal g on g.definition = d and g.contestant = :contestant
    where o.child = :childDefinition
    """)
    List<GoalObjectiveMatch> findObjectivesWithOptionalGoal(@Param("childDefinition") GoalDefinition childDefinition,
                                                            @Param("contestant") Contestant contestant);

    @Query("""
    select new com.lingosphinx.gamification.repository.GoalObjectiveMatch(childGoal, o)
    from Goal parentGoal
    join parentGoal.definition d
    join d.objectives o
    join Goal childGoal on childGoal.definition = o.child and childGoal.contestant = :contestant
    where parentGoal.definition in :parentDefinitions and parentGoal.contestant = :contestant
    """)
    List<GoalObjectiveMatch> findChildObjectives(@Param("parentDefinitions") List<GoalDefinition> parentDefinitions,
                                                 @Param("contestant") Contestant contestant);
}
