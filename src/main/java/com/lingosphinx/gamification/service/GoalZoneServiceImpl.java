package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.GoalZoneDto;
import com.lingosphinx.gamification.mapper.GoalZoneMapper;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalZoneServiceImpl implements GoalZoneService {

    private final GoalZoneRepository goalZoneRepository;
    private final GoalZoneMapper goalZoneMapper;

    @Override
    public GoalZoneDto create(GoalZoneDto goalZoneDto) {
        var goalZone = goalZoneMapper.toEntity(goalZoneDto);
        var savedGoalZone = goalZoneRepository.save(goalZone);
        log.info("GoalZone created successfully: id={}", savedGoalZone.getId());
        return goalZoneMapper.toDto(savedGoalZone);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalZoneDto readById(Long id) {
        var goalZone = goalZoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GoalZone not found"));
        log.info("GoalZone read successfully: id={}", id);
        return goalZoneMapper.toDto(goalZone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalZoneDto> readAll() {
        var result = goalZoneRepository.findAll().stream()
                .map(goalZoneMapper::toDto)
                .toList();
        log.info("All goalZones read successfully, count={}", result.size());
        return result;
    }

    @Override
    public GoalZoneDto update(Long id, GoalZoneDto goalZoneDto) {
        var existingGoalZone = goalZoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GoalZone not found"));

        goalZoneMapper.toEntityFromDto(goalZoneDto, existingGoalZone);
        log.info("GoalZone updated successfully: id={}", existingGoalZone.getId());
        return goalZoneMapper.toDto(existingGoalZone);
    }

    @Override
    public void delete(Long id) {
        goalZoneRepository.deleteById(id);
        log.info("GoalZone deleted successfully: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalZoneDto readByName(String name) {
        var goalZone = goalZoneRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("GoalZone not found for name=" + name + "."));
        log.info("GoalZone read by name: name={}, id={}", name, goalZone.getId());
        return goalZoneMapper.toDto(goalZone);
    }
}