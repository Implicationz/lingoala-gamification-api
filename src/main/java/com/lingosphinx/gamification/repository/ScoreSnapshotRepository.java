package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.domain.RenewalType;
import com.lingosphinx.gamification.domain.ScoreSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface ScoreSnapshotRepository extends JpaRepository<ScoreSnapshot, Long> {

    @Modifying
    @Query("""
    update ScoreSnapshot s
    set s.amount = s.score.amount
    where s.score.contestant.timeZone = :timeZone
      and s.score.lastProgress >= :begin
      and s.score.lastProgress < :end
      and s.renewalType = :renewalType
    """)
    int updateSnapshotsInInterval(@Param("timeZone") IanaTimeZone timeZone,
                                @Param("begin") Instant begin,
                                  @Param("end") Instant end,
                                  @Param("renewalType") RenewalType renewalType);
}