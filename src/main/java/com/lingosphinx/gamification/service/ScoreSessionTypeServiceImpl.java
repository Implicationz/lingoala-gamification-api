package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreSessionTypeDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ScoreSessionTypeMapper;
import com.lingosphinx.gamification.repository.ScoreSessionTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScoreSessionTypeServiceImpl implements ScoreSessionTypeService {

    private final ScoreSessionTypeRepository scoreSessionTypeRepository;
    private final ScoreSessionTypeMapper scoreSessionTypeMapper;

    @Override
    public ScoreSessionTypeDto create(ScoreSessionTypeDto scoreSessionTypeDto) {
        var scoreSessionType = scoreSessionTypeMapper.toEntity(scoreSessionTypeDto);
        var savedScoreSessionType = scoreSessionTypeRepository.save(scoreSessionType);
        log.info("ScoreSessionType created successfully: id={}", savedScoreSessionType.getId());
        return scoreSessionTypeMapper.toDto(savedScoreSessionType);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreSessionTypeDto readById(Long id) {
        var scoreSessionType = scoreSessionTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreSessionType not found"));
        log.info("ScoreSessionType read successfully: id={}", id);
        return scoreSessionTypeMapper.toDto(scoreSessionType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScoreSessionTypeDto> readAll() {
        var result = scoreSessionTypeRepository.findAll().stream()
                .map(scoreSessionTypeMapper::toDto)
                .toList();
        log.info("All scoreSessionTypes read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ScoreSessionTypeDto update(Long id, ScoreSessionTypeDto scoreSessionTypeDto) {
        var existingScoreSessionType = scoreSessionTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreSessionType not found"));

        scoreSessionTypeMapper.toEntityFromDto(scoreSessionTypeDto, existingScoreSessionType);
        log.info("ScoreSessionType updated successfully: id={}", existingScoreSessionType.getId());
        return scoreSessionTypeMapper.toDto(existingScoreSessionType);
    }

    @Override
    public void delete(Long id) {
        scoreSessionTypeRepository.deleteById(id);
        log.info("ScoreSessionType deleted successfully: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "scoreSessionTypeReadByName", key = "#name")
    public ScoreSessionTypeDto readByName(String name) {
        var scoreSessionType = scoreSessionTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreSessionType not found for name=" + name + "."));
        log.info("ScoreSessionType read by name: name={}, id={}", name, scoreSessionType.getId());
        return scoreSessionTypeMapper.toDto(scoreSessionType);
    }
}