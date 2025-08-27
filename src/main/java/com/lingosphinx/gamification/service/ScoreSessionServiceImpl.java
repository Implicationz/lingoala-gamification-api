package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreSessionDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ScoreSessionMapper;
import com.lingosphinx.gamification.repository.ScoreRepository;
import com.lingosphinx.gamification.repository.ScoreSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScoreSessionServiceImpl implements ScoreSessionService {

    private final ContestantService contestantService;
    private final ScoreSessionRepository scoreSessionRepository;
    private final ScoreSessionMapper scoreSessionMapper;
    private final ScoreRepository scoreRepository;

    @Override
    public ScoreSessionDto create(ScoreSessionDto scoreSessionDto) {
        var scoreSession = scoreSessionMapper.toEntity(scoreSessionDto);
        var savedScoreSession = scoreSessionRepository.save(scoreSession);
        log.info("ScoreSession created successfully: id={}", savedScoreSession.getId());
        return scoreSessionMapper.toDto(savedScoreSession);
    }

    @Override
    public ScoreSessionDto createForCurrentContestant(ScoreSessionDto scoreSessionDto) {
        var scoreSession = scoreSessionMapper.toEntity(scoreSessionDto);
        var contestant = contestantService.readCurrentContestant();
        scoreSession.setContestant(contestant);

        var scoreIds = scoreSession.getEvents().stream()
                .map(event -> event.getScore().getId())
                .distinct()
                .toList();

        var scores = scoreRepository.findAllById(scoreIds)
                .stream()
                .collect(Collectors.toMap(score -> score.getId(), score -> score));

        for (var event : scoreSession.getEvents()) {
            var score = scores.get(event.getScore().getId());
            if (score == null) {
                throw new ResourceNotFoundException("Score not found");
            }
            score.apply(event);
            scoreRepository.save(score);
        }

        var savedScoreSession = scoreSessionRepository.save(scoreSession);
        log.info("ScoreSession created successfully: id={}", savedScoreSession.getId());
        return scoreSessionMapper.toDto(savedScoreSession);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreSessionDto readById(Long id) {
        var scoreSession = scoreSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreSession not found"));
        return this.scoreSessionMapper.toDto(scoreSession);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ScoreSessionDto> readAll() {
        var result = scoreSessionRepository.findAll().stream()
                .map(scoreSessionMapper::toDto)
                .toList();
        log.info("All scoreSessions read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ScoreSessionDto update(Long id, ScoreSessionDto scoreSessionDto) {
        var existingScoreSession = scoreSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreSession not found"));

        scoreSessionMapper.toEntityFromDto(scoreSessionDto, existingScoreSession);
        log.info("ScoreSession updated successfully: id={}", existingScoreSession.getId());
        return scoreSessionMapper.toDto(existingScoreSession);
    }

    @Override
    public void delete(Long id) {
        scoreSessionRepository.deleteById(id);
        log.info("ScoreSession deleted successfully: id={}", id);
    }

}