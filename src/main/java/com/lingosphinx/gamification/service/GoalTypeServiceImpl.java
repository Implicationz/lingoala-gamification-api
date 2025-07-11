package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.GoalTypeDto;
import com.lingosphinx.gamification.mapper.GoalTypeMapper;
import com.lingosphinx.gamification.repository.GoalTypeRepository;
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
public class GoalTypeServiceImpl implements GoalTypeService {

    private final GoalTypeRepository goalTypeRepository;
    private final GoalTypeMapper goalTypeMapper;

    @Override
    public GoalTypeDto create(GoalTypeDto goalTypeDto) {
        var goalType = goalTypeMapper.toEntity(goalTypeDto);
        var savedGoalType = goalTypeRepository.save(goalType);
        log.info("GoalType created successfully: id={}", savedGoalType.getId());
        return goalTypeMapper.toDto(savedGoalType);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalTypeDto readById(Long id) {
        var goalType = goalTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GoalType not found"));
        log.info("GoalType read successfully: id={}", id);
        return goalTypeMapper.toDto(goalType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalTypeDto> readAll() {
        var result = goalTypeRepository.findAll().stream()
                .map(goalTypeMapper::toDto)
                .toList();
        log.info("All goalTypes read successfully, count={}", result.size());
        return result;
    }

    @Override
    public GoalTypeDto update(Long id, GoalTypeDto goalTypeDto) {
        var existingGoalType = goalTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GoalType not found"));

        goalTypeMapper.toEntityFromDto(goalTypeDto, existingGoalType);
        log.info("GoalType updated successfully: id={}", existingGoalType.getId());
        return goalTypeMapper.toDto(existingGoalType);
    }

    @Override
    public void delete(Long id) {
        goalTypeRepository.deleteById(id);
        log.info("GoalType deleted successfully: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalTypeDto readByName(String name) {
        var goalType = goalTypeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("GoalType not found for name=" + name + "."));
        log.info("GoalType read by name: name={}, id={}", name, goalType.getId());
        return goalTypeMapper.toDto(goalType);
    }
}