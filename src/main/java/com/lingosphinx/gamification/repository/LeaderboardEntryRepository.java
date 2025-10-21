package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaderboardEntryRepository extends JpaRepository<LeaderboardEntry, Long>, JpaSpecificationExecutor<LeaderboardEntry> {

    @Query(value = """
    SELECT * FROM leaderboard_entry
    WHERE leaderboard_id = :leaderboardId
    ORDER BY progress DESC
    LIMIT :k
    """, nativeQuery = true)
    List<LeaderboardEntry> findTopKByLeaderboard(@Param("leaderboardId") Long leaderboardId, @Param("k") int k);

    @Query(value = """
    SELECT le.*, 
      (SELECT COUNT(*) + 1 FROM leaderboard_entry le2 
       WHERE le2.leaderboard_id = le.leaderboard_id 
         AND le2.progress > le.progress) AS rank
    FROM leaderboard_entry le
    WHERE le.leaderboard_id = :leaderboardId AND le.contestant_id = :contestantId
    """, nativeQuery = true)
    Object[] findEntryAndRank(@Param("leaderboardId") Long leaderboardId, @Param("contestantId") Long contestantId);

    @Query(value = """
    SELECT * FROM leaderboard_entry
    WHERE leaderboard_id = :leaderboardId
    ORDER BY progress DESC
    OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY
    """, nativeQuery = true)
    List<LeaderboardEntry> findEntriesAroundRank(@Param("leaderboardId") Long leaderboardId, @Param("offset") int offset, @Param("limit") int limit);

}
