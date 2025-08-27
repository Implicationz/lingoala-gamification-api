package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Score;
import com.lingosphinx.gamification.dto.ScoreActivationDto;
import com.lingosphinx.gamification.dto.ScoreDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ScoreMapper;
import com.lingosphinx.gamification.repository.ScoreDefinitionRepository;
import com.lingosphinx.gamification.repository.ScoreRepository;
import com.lingosphinx.gamification.repository.ScoreSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScoreServiceImpl implements ScoreService {

    private final ContestantService contestantService;
    private final ScoreDefinitionRepository scoreDefinitionRepository;
    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;


    @Override
    public ScoreDto create(ScoreDto scoreDto) {
        var score = scoreMapper.toEntity(scoreDto);
        var savedScore = scoreRepository.save(score);
        log.info("Score created successfully: id={}", savedScore.getId());
        return scoreMapper.toDto(savedScore);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreDto readById(Long id) {
        var score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found"));
        return this.scoreMapper.toDto(score);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ScoreDto> readAll() {
        var result = scoreRepository.findAll().stream()
                .map(scoreMapper::toDto)
                .toList();
        log.info("All scores read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ScoreDto update(Long id, ScoreDto scoreDto) {
        var existingScore = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found"));

        scoreMapper.toEntityFromDto(scoreDto, existingScore);
        log.info("Score updated successfully: id={}", existingScore.getId());
        return scoreMapper.toDto(existingScore);
    }

    @Override
    public void delete(Long id) {
        scoreRepository.deleteById(id);
        log.info("Score deleted successfully: id={}", id);
    }

    @Override
    public ScoreDto activate(ScoreActivationDto scoreActivation) {
        var contestant = contestantService.readCurrentContestant();
        var spec = ScoreSpecifications.byZoneTypeAndContestant(scoreActivation.getZone(), scoreActivation.getType(), contestant);
        var found = scoreRepository.findOne(spec);

        var activated = found.orElseGet(() -> {
            var definition = scoreDefinitionRepository.findByZone_NameAndType_Name(scoreActivation.getZone(), scoreActivation.getType())
                    .orElseThrow(() -> new IllegalArgumentException("ScoreDefinition is invalid."));
            var score = Score.fromDefinition(definition);
            score.setContestant(contestant);
            var savedScore = scoreRepository.save(score);
            log.info("Score activated successfully: id={}", savedScore.getId());
            return savedScore;
        });
        return this.scoreMapper.toDto(activated);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreDto readByZoneAndType(String zone, String type) {
        var contestant = contestantService.readCurrentContestant();
        var spec = ScoreSpecifications.byZoneTypeAndContestant(zone, type, contestant);
        var score = scoreRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found for zone=" + zone + " and type=" + type));
        log.info("Score read by zone and type: zone={}, type={}, id={}", zone, type, score.getId());
        return scoreMapper.toDto(score);
    }

}