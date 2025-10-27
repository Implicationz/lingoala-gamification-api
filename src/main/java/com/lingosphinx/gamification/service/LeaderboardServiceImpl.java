package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.ExperienceProgress;
import com.lingosphinx.gamification.domain.ExperienceValue;
import com.lingosphinx.gamification.domain.LeaderboardEntry;
import com.lingosphinx.gamification.dto.LeaderboardDto;
import com.lingosphinx.gamification.dto.LeaderboardSearch;
import com.lingosphinx.gamification.dto.LeaderboardSearchEntry;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.LeaderboardMapper;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import com.lingosphinx.gamification.repository.LeaderboardEntryRepository;
import com.lingosphinx.gamification.repository.LeaderboardEntryWithRank;
import com.lingosphinx.gamification.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LeaderboardServiceImpl implements LeaderboardService {

    private final ContestantService contestantService;
    private final GoalZoneRepository goalZoneRepository;
    private final LeaderboardEntryRepository leaderboardEntryRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final LeaderboardMapper leaderboardMapper;


    @Override
    public LeaderboardDto create(LeaderboardDto leaderboardDto) {
        var leaderboard = leaderboardMapper.toEntity(leaderboardDto);
        var savedLeaderboard = leaderboardRepository.save(leaderboard);
        log.info("Leaderboard created successfully: id={}", savedLeaderboard.getId());
        return leaderboardMapper.toDto(savedLeaderboard);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaderboardDto readById(Long id) {
        var leaderboard = leaderboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leaderboard not found"));
        return this.leaderboardMapper.toDto(leaderboard);
    }


    @Override
    @Transactional(readOnly = true)
    public List<LeaderboardDto> readAll() {
        var result = leaderboardRepository.findAll().stream()
                .map(leaderboardMapper::toDto)
                .toList();
        log.info("All leaderboards read successfully, count={}", result.size());
        return result;
    }

    @Override
    public LeaderboardDto update(Long id, LeaderboardDto leaderboardDto) {
        var existingLeaderboard = leaderboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leaderboard not found"));

        leaderboardMapper.toEntityFromDto(leaderboardDto, existingLeaderboard);
        log.info("Leaderboard updated successfully: id={}", existingLeaderboard.getId());
        return leaderboardMapper.toDto(existingLeaderboard);
    }

    @Override
    public void delete(Long id) {
        leaderboardRepository.deleteById(id);
        log.info("Leaderboard deleted successfully: id={}", id);
    }

    protected List<LeaderboardEntryWithRank> searchRankings(LeaderboardSearch leaderboardSearch) {
        var zone = goalZoneRepository.getReferenceById(leaderboardSearch.getZone().getId());
        var leaderboard = leaderboardRepository
                .findByZoneAndRenewalType(zone, leaderboardSearch.getRenewalType())
                .orElseThrow(() -> new ResourceNotFoundException("Leaderboard not found for zone: " + zone));

        var contestant = contestantService.readCurrentContestant();

        var topEntries = leaderboardEntryRepository.findTopK(leaderboard.getId(), 10);
        var contestantEntryWithRank = leaderboardEntryRepository
                .findEntryAndRankByLeaderboardIdAndContestantId(leaderboard.getId(), contestant.getId())
                .orElse(null);

        List<LeaderboardEntryWithRank> aroundEntries = List.of();
        if (contestantEntryWithRank != null && contestantEntryWithRank.getRank() > 10) {
            var contestantRank = contestantEntryWithRank.getRank();
            var startRank = Math.max(1, contestantRank - 5);
            var endRank = contestantRank + 5;

            aroundEntries = leaderboardEntryRepository.findEntriesAroundRank(
                    leaderboard.getId(), startRank, endRank);
        }

        var allEntries = Stream.concat(
                topEntries.stream(),
                aroundEntries.stream()
        ).toList();
        return allEntries;
    }

    @Override
    public LeaderboardSearch search(LeaderboardSearch leaderboardSearch) {
        var allEntries = searchRankings(leaderboardSearch);
        var idToRank = allEntries.stream()
                .collect(Collectors.toMap(
                    LeaderboardEntryWithRank::getId,
                    Function.identity()
                ));

        var allIds = idToRank.keySet();
        var entries = leaderboardEntryRepository
            .findAllById(allIds)
            .stream()
            .map(entry -> {
                var dto = leaderboardMapper.toDto(entry);
                var searchEntry = LeaderboardSearchEntry.builder()
                        .id(entry.getId())
                        .contestant(dto.getContestant())
                        .build();

                var foundEntry = idToRank.get(entry.getId());
                searchEntry.setProgress(ExperienceValue.valueOf(foundEntry.getProgress()));
                searchEntry.setRank(foundEntry.getRank());
                return searchEntry;
            }).toList();

        leaderboardSearch.setEntries(entries);
        return leaderboardSearch;
    }

    @Override
    public void progress(ExperienceProgress progress) {
        var participation = progress.getContestant();
        var goalZone = participation.getZone();
        var entries = leaderboardEntryRepository
                .findAllByLeaderboard_ZoneAndContestant(goalZone, participation.getContestant())
                .stream().toList();

        if (entries.isEmpty()) {
            var leaderboards = leaderboardRepository.findAllByZone(goalZone);
            for (var lb : leaderboards) {
                var defaultEntry = LeaderboardEntry.builder()
                        .leaderboard(lb)
                        .contestant(participation.getContestant())
                        .progress(progress.getExperience())
                        .build();
                leaderboardEntryRepository.save(defaultEntry);
            }
            return;
        }

        for (var entry : entries) {
            var newProgress = entry.getProgress().add(progress.getExperience());
            entry.setProgress(newProgress);
        }

    }
}