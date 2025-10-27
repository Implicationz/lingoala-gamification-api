package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.GoalZone;
import com.lingosphinx.gamification.domain.LeaderboardEntry;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeaderboardEntryRepository extends JpaRepository<LeaderboardEntry, Long>, JpaSpecificationExecutor<LeaderboardEntry> {

    @Query(value = """
    WITH ranked AS (
      SELECT le.id,
             le.leaderboard_id,
             le.contestant_id,
             le.progress,
             RANK() OVER (ORDER BY le.progress DESC) AS rank
      FROM leaderboard_entry le
      WHERE le.leaderboard_id = :leaderboardId
    )
    SELECT id,
           progress,
           rank
    FROM ranked
    WHERE rank <= :k
    ORDER BY rank, progress DESC
    """, nativeQuery = true)
    List<LeaderboardEntryWithRank> findTopK(
            @Param("leaderboardId") Long leaderboardId,
            @Param("k") int k
    );

    @Query(value = """
    SELECT id,
           progress,
           rnk AS rank
    FROM (
      SELECT le.id,
             le.leaderboard_id,
             le.contestant_id,
             le.progress,
             RANK() OVER (ORDER BY le.progress DESC) AS rnk
      FROM leaderboard_entry le
      WHERE le.leaderboard_id = :leaderboardId
    ) sub
    WHERE contestant_id = :contestantId
    """, nativeQuery = true)
    Optional<LeaderboardEntryWithRank> findEntryAndRankByLeaderboardIdAndContestantId(
            @Param("leaderboardId") Long leaderboardId,
            @Param("contestantId") Long contestantId
    );

    @Query(value = """
    WITH ranked AS (
      SELECT le.id,
             le.progress,
             RANK() OVER (ORDER BY le.progress DESC) AS rank
      FROM leaderboard_entry le
      WHERE le.leaderboard_id = :leaderboardId
    )
    SELECT id,
           progress,
           rank
    FROM ranked
    WHERE rank BETWEEN :startRank AND :endRank
    ORDER BY rank, progress DESC
    """, nativeQuery = true)
    List<LeaderboardEntryWithRank> findEntriesAroundRank(
            @Param("leaderboardId") Long leaderboardId,
            @Param("startRank") long startRank,
            @Param("endRank") long endRank
    );

    @EntityGraph(attributePaths = {"contestant"})
    List<LeaderboardEntry> findAllById(Iterable<Long> ids);

    List<LeaderboardEntry> findAllByLeaderboard_ZoneAndContestant(GoalZone goalZone, Contestant contestant);
}
