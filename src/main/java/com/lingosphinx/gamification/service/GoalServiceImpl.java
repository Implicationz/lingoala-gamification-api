package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.mapper.GoalMapper;
import com.lingosphinx.gamification.repository.GoalRepository;
import com.lingosphinx.gamification.repository.GoalSpecifications;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final UserService userService;

    @Override
    public GoalDto create(GoalDto goalDto) {
        var goal = goalMapper.toEntity(goalDto);
        var savedGoal = goalRepository.save(goal);
        log.info("Goal created successfully: id={}", savedGoal.getId());
        return goalMapper.toDto(savedGoal);
    }

    @Override
    public GoalDto createByCurrentUser(GoalDto goalDto) {
        var goal = goalMapper.toEntity(goalDto);
        var userId = userService.getCurrentUserId();
        goal.setUserId(userId);
        var savedGoal = goalRepository.save(goal);
        log.info("Goal created successfully for user={}: id={}",  userId, savedGoal.getId());
        return goalMapper.toDto(savedGoal);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalDto readById(Long id) {
        var goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        log.info("Goal read successfully: id={}", id);
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalDto> readAll() {
        var result = goalRepository.findAll().stream()
                .map(goalMapper::toDto)
                .toList();
        log.info("All goals read successfully, count={}", result.size());
        return result;
    }

    @Override
    public GoalDto update(Long id, GoalDto goalDto) {
        var existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        goalMapper.toEntityFromDto(goalDto, existingGoal);
        log.info("Goal updated successfully: id={}", existingGoal.getId());
        return goalMapper.toDto(existingGoal);
    }

    @Override
    public void delete(Long id) {
        goalRepository.deleteById(id);
        log.info("Goal deleted successfully: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalDto readByTypeNameAndReference(String type, String reference) {
        var spec = GoalSpecifications.byTypeNameAndReference(type, reference);
        var goal = goalRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found for type=" + type + " and reference=" + reference));
        log.info("Goal read by type and reference: type={}, reference={}, id={}", type, reference, goal.getId());
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalDto> readAllByZoneNameAndTypeName(String zone, String type) {
        var spec = GoalSpecifications.byZoneNameAndTypeName(zone, type);
        var goals = goalRepository.findAll(spec).stream()
                .map(goalMapper::toDto)
                .toList();
        log.info("Goals read by zone and type: zone={}, type={}, count={}", zone, type, goals.size());
        return goals;
    }
}