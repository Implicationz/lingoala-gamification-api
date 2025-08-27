package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.HabitDefinitionDto;
import com.lingosphinx.gamification.dto.HabitDefinitionRegistrationDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.HabitDefinitionMapper;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import com.lingosphinx.gamification.repository.HabitDefinitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitDefinitionServiceImpl implements HabitDefinitionService {

    private final HabitDefinitionRepository habitDefinitionRepository;
    private final GoalZoneRepository goalZoneRepository;
    private final HabitDefinitionMapper habitDefinitionMapper;

    @Override
    public HabitDefinitionDto create(HabitDefinitionDto habitDefinitionDto) {
        var habitDefinition = habitDefinitionMapper.toEntity(habitDefinitionDto);
        var savedHabitDefinition = habitDefinitionRepository.save(habitDefinition);
        log.info("HabitDefinition created successfully: id={}", savedHabitDefinition.getId());
        return habitDefinitionMapper.toDto(savedHabitDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitDefinitionDto readById(Long id) {
        var habitDefinition = habitDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HabitDefinition not found"));
        log.info("HabitDefinition read successfully: id={}", id);
        return habitDefinitionMapper.toDto(habitDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitDefinitionDto> readAll() {
        var result = habitDefinitionRepository.findAll().stream()
                .map(habitDefinitionMapper::toDto)
                .toList();
        log.info("All habitDefinitions read successfully, count={}", result.size());
        return result;
    }

    @Override
    public HabitDefinitionDto update(Long id, HabitDefinitionDto habitDefinitionDto) {
        var existingHabitDefinition = habitDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HabitDefinition not found"));

        habitDefinitionMapper.updateEntityFromDto(habitDefinitionDto, existingHabitDefinition);

        if (habitDefinitionDto.getZone() != null && habitDefinitionDto.getZone().getId() != null) {
            existingHabitDefinition.setZone(goalZoneRepository.getReferenceById(habitDefinitionDto.getZone().getId()));
        }

        log.info("HabitDefinition updated successfully: id={}", existingHabitDefinition.getId());
        return habitDefinitionMapper.toDto(existingHabitDefinition);
    }

    @Override
    public void delete(Long id) {
        habitDefinitionRepository.deleteById(id);
        log.info("HabitDefinition deleted successfully: id={}", id);
    }

    @Override
    public HabitDefinitionDto register(HabitDefinitionRegistrationDto habitDefinitionRegistration) {
        var found = habitDefinitionRepository.findByZone_NameAndName(
                habitDefinitionRegistration.getZone(),
                habitDefinitionRegistration.getName()
        );

        var registered = found.orElseGet(() -> {
            var habitDefinition = habitDefinitionMapper.toEntityFromRegistration(habitDefinitionRegistration);
            var zone = goalZoneRepository.findByName(habitDefinitionRegistration.getZone()).orElseThrow();
            habitDefinition.setZone(zone);
            var savedHabitDefinition = habitDefinitionRepository.save(habitDefinition);
            log.info("HabitDefinition registered successfully: id={}", savedHabitDefinition.getId());
            return savedHabitDefinition;
        });
        return habitDefinitionMapper.toDto(registered);
    }
}