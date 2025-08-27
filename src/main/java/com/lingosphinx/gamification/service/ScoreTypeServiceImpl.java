package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreTypeDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.ScoreTypeMapper;
import com.lingosphinx.gamification.repository.ScoreTypeRepository;
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
public class ScoreTypeServiceImpl implements ScoreTypeService {

    private final ScoreTypeRepository scoreTypeRepository;
    private final ScoreTypeMapper scoreTypeMapper;

    @Override
    public ScoreTypeDto create(ScoreTypeDto scoreTypeDto) {
        var scoreType = scoreTypeMapper.toEntity(scoreTypeDto);
        var savedScoreType = scoreTypeRepository.save(scoreType);
        log.info("ScoreType created successfully: id={}", savedScoreType.getId());
        return scoreTypeMapper.toDto(savedScoreType);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreTypeDto readById(Long id) {
        var scoreType = scoreTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreType not found"));
        log.info("ScoreType read successfully: id={}", id);
        return scoreTypeMapper.toDto(scoreType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScoreTypeDto> readAll() {
        var result = scoreTypeRepository.findAll().stream()
                .map(scoreTypeMapper::toDto)
                .toList();
        log.info("All scoreTypes read successfully, count={}", result.size());
        return result;
    }

    @Override
    public ScoreTypeDto update(Long id, ScoreTypeDto scoreTypeDto) {
        var existingScoreType = scoreTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreType not found"));

        scoreTypeMapper.toEntityFromDto(scoreTypeDto, existingScoreType);
        log.info("ScoreType updated successfully: id={}", existingScoreType.getId());
        return scoreTypeMapper.toDto(existingScoreType);
    }

    @Override
    public void delete(Long id) {
        scoreTypeRepository.deleteById(id);
        log.info("ScoreType deleted successfully: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "scoreTypeReadByName", key = "#name")
    public ScoreTypeDto readByName(String name) {
        var scoreType = scoreTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("ScoreType not found for name=" + name + "."));
        log.info("ScoreType read by name: name={}, id={}", name, scoreType.getId());
        return scoreTypeMapper.toDto(scoreType);
    }
}