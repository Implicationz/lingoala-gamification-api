package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreDefinitionDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ScoreDefinitionMapper;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import com.lingosphinx.gamification.repository.ScoreDefinitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScoreDefinitionServiceImpl implements ScoreDefinitionService {

    private final ScoreDefinitionRepository scoreDefinitionRepository;
    private final GoalZoneRepository goalZoneRepository;
    private final ScoreDefinitionMapper scoreDefinitionMapper;

    @Override
    public ScoreDefinitionDto create(ScoreDefinitionDto scoreDefinitionDto) {
        var scoreDefinition = scoreDefinitionMapper.toEntity(scoreDefinitionDto);
        var savedScoreDefinition = scoreDefinitionRepository.save(scoreDefinition);
        log.info("ScoreDefinition created successfully: id={}", savedScoreDefinition.getId());
        return scoreDefinitionMapper.toDto(savedScoreDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreDefinitionDto readById(Long id) {
        var scoreDefinition = scoreDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreDefinition not found"));
        log.info("ScoreDefinition read successfully: id={}", id);
        return scoreDefinitionMapper.toDto(scoreDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScoreDefinitionDto> readAll() {
        var result = scoreDefinitionRepository.findAll().stream()
                .map(scoreDefinitionMapper::toDto)
                .toList();
        log.info("All scoreDefinitions read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ScoreDefinitionDto update(Long id, ScoreDefinitionDto scoreDefinitionDto) {
        var existingScoreDefinition = scoreDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreDefinition not found"));

        scoreDefinitionMapper.updateEntityFromDto(scoreDefinitionDto, existingScoreDefinition);

        if (scoreDefinitionDto.getZone() != null && scoreDefinitionDto.getZone().getId() != null) {
            existingScoreDefinition.setZone(goalZoneRepository.getReferenceById(scoreDefinitionDto.getZone().getId()));
        }

        log.info("ScoreDefinition updated successfully: id={}", existingScoreDefinition.getId());
        return scoreDefinitionMapper.toDto(existingScoreDefinition);
    }

    @Override
    public void delete(Long id) {
        scoreDefinitionRepository.deleteById(id);
        log.info("ScoreDefinition deleted successfully: id={}", id);
    }
}