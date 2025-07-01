package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.mapper.GoalDefinitionMapper;
import com.lingosphinx.gamification.repository.GoalDefinitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StreakServiceImpl implements StreakService {


    @Override
    public GoalDefinitionDto create(GoalDefinitionDto goalDefinition) {
        return null;
    }

    @Override
    public GoalDefinitionDto readById(Long id) {
        return null;
    }

    @Override
    public List<GoalDefinitionDto> readAll() {
        return List.of();
    }

    @Override
    public GoalDefinitionDto update(Long id, GoalDefinitionDto goalDefinition) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}