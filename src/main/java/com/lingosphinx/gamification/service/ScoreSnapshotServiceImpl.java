package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.RenewalType;
import com.lingosphinx.gamification.dto.ScoreSnapshotDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ScoreSnapshotMapper;
import com.lingosphinx.gamification.repository.ScoreSnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScoreSnapshotServiceImpl implements ScoreSnapshotService {

    private final ScoreSnapshotRepository scoreSnapshotRepository;
    private final ScoreSnapshotMapper scoreSnapshotMapper;


    @Override
    public ScoreSnapshotDto create(ScoreSnapshotDto scoreSnapshotDto) {
        var scoreSnapshot = scoreSnapshotMapper.toEntity(scoreSnapshotDto);
        var savedScoreSnapshot = scoreSnapshotRepository.save(scoreSnapshot);
        log.info("ScoreSnapshot created successfully: id={}", savedScoreSnapshot.getId());
        return scoreSnapshotMapper.toDto(savedScoreSnapshot);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreSnapshotDto readById(Long id) {
        var scoreSnapshot = scoreSnapshotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreSnapshot not found"));
        return this.scoreSnapshotMapper.toDto(scoreSnapshot);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ScoreSnapshotDto> readAll() {
        var result = scoreSnapshotRepository.findAll().stream()
                .map(scoreSnapshotMapper::toDto)
                .toList();
        log.info("All scoreSnapshots read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ScoreSnapshotDto update(Long id, ScoreSnapshotDto scoreSnapshotDto) {
        var existingScoreSnapshot = scoreSnapshotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreSnapshot not found"));

        scoreSnapshotMapper.toEntityFromDto(scoreSnapshotDto, existingScoreSnapshot);
        log.info("ScoreSnapshot updated successfully: id={}", existingScoreSnapshot.getId());
        return scoreSnapshotMapper.toDto(existingScoreSnapshot);
    }

    @Override
    public void delete(Long id) {
        scoreSnapshotRepository.deleteById(id);
        log.info("ScoreSnapshot deleted successfully: id={}", id);
    }


    // src/main/java/com/lingosphinx/gamification/service/ScoreSnapshotServiceImpl.java

    @Override
    public void regenerateAll() {
        var now = ZonedDateTime.now();
        var day = now.getDayOfMonth();
        var month = now.getMonthValue();

        record Interval(RenewalType type, ZonedDateTime begin, ZonedDateTime end, boolean shouldRun) {}

        var intervals = List.of(
            new Interval(
                    RenewalType.DAILY,
                    now.toLocalDate().atStartOfDay(now.getZone()),
                    now.toLocalDate().atStartOfDay(now.getZone()).plusDays(1),
                    true
            ),
            new Interval(
                    RenewalType.WEEKLY,
                    now.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay(now.getZone()),
                    now.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay(now.getZone()).plusWeeks(1),
                    now.getDayOfWeek().getValue() == 1
            ),
            new Interval(
                    RenewalType.MONTHLY,
                    now.withDayOfMonth(1).toLocalDate().atStartOfDay(now.getZone()),
                    now.withDayOfMonth(1).toLocalDate().atStartOfDay(now.getZone()).plusMonths(1),
                    day == 1
            ),
            new Interval(
                    RenewalType.YEARLY,
                    now.withDayOfYear(1).toLocalDate().atStartOfDay(now.getZone()),
                    now.withDayOfYear(1).toLocalDate().atStartOfDay(now.getZone()).plusYears(1),
                    day == 1 && month == 1
            )
        );

        var results = intervals.stream()
                .map(i -> i.shouldRun() ? scoreSnapshotRepository.updateSnapshotsInInterval(i.begin().toInstant(), i.end().toInstant(), i.type()) : 0)
                .toList();

        log.info("Snapshots regenerated: daily={}, weekly={}, monthly={}, yearly={}", results.get(0), results.get(1), results.get(2), results.get(3));
    }

}