package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDefinitionRegistrationDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.GoalDefinitionMapper;
import com.lingosphinx.gamification.repository.GoalDefinitionRepository;
import com.lingosphinx.gamification.repository.GoalDefinitionSpecifications;
import com.lingosphinx.gamification.repository.GoalTypeRepository;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalDefinitionServiceImpl implements GoalDefinitionService {

    private final GoalDefinitionRepository goalDefinitionRepository;
    private final GoalTypeRepository goalTypeRepository;
    private final GoalZoneRepository goalZoneRepository;
    private final GoalDefinitionMapper goalDefinitionMapper;

    @Override
    public GoalDefinitionDto create(GoalDefinitionDto goalDefinitionDto) {
        var goalDefinition = goalDefinitionMapper.toEntity(goalDefinitionDto);
        var savedGoalDefinition = goalDefinitionRepository.save(goalDefinition);
        log.info("GoalDefinition created successfully: id={}", savedGoalDefinition.getId());
        return goalDefinitionMapper.toDto(savedGoalDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalDefinitionDto readById(Long id) {
        var goalDefinition = goalDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GoalDefinition not found"));
        log.info("GoalDefinition read successfully: id={}", id);
        return goalDefinitionMapper.toDto(goalDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalDefinitionDto> readAll() {
        var result = goalDefinitionRepository.findAll().stream()
                .map(goalDefinitionMapper::toDto)
                .toList();
        log.info("All goalDefinitions read successfully, count={}", result.size());
        return result;
    }

    @Override
    public GoalDefinitionDto update(Long id, GoalDefinitionDto goalDefinitionDto) {
        var existingGoalDefinition = goalDefinitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GoalDefinition not found"));

        goalDefinitionMapper.updateEntityFromDto(goalDefinitionDto, existingGoalDefinition);

        if (goalDefinitionDto.getType() != null && goalDefinitionDto.getType().getId() != null) {
            existingGoalDefinition.setType(goalTypeRepository.getReferenceById(goalDefinitionDto.getType().getId()));
        }
        if (goalDefinitionDto.getZone() != null && goalDefinitionDto.getZone().getId() != null) {
            existingGoalDefinition.setZone(goalZoneRepository.getReferenceById(goalDefinitionDto.getZone().getId()));
        }
        if (goalDefinitionDto.getParent() != null && goalDefinitionDto.getParent().getId() != null) {
            existingGoalDefinition.setParent(goalDefinitionRepository.getReferenceById(goalDefinitionDto.getParent().getId()));
        }

       var  childIds = goalDefinitionDto.getChildren().stream()
                .map(GoalDefinitionDto::getId)
                .filter(Objects::nonNull)
                .toList();

        var children = existingGoalDefinition.getChildren();
        children.clear();

        var persistedChildren = goalDefinitionRepository.findAllById(childIds);
        for (var child : persistedChildren) {
            child.setParent(existingGoalDefinition);
            children.add(child);

        }

        log.info("GoalDefinition updated successfully: id={}", existingGoalDefinition.getId());
        return goalDefinitionMapper.toDto(existingGoalDefinition);
    }

    @Override
    public void delete(Long id) {
        goalDefinitionRepository.deleteById(id);
        log.info("GoalDefinition deleted successfully: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalDefinitionDto readByTypeNameAndReference(String type, String reference) {
        var spec = GoalDefinitionSpecifications.byTypeNameAndReference(type, reference);
        var goalDefinition = goalDefinitionRepository.findOne(spec)
                .orElseThrow(() -> new ResourceNotFoundException("GoalDefinition not found for type=" + type + " and reference=" + reference));
        log.info("GoalDefinition read by type and reference: type={}, reference={}, id={}", type, reference, goalDefinition.getId());
        return goalDefinitionMapper.toDto(goalDefinition);
    }


    public GoalDefinition findOrCreate(GoalDefinitionRegistrationDto goalDefinitionRegistration) {
        var found = goalDefinitionRepository.findByZone_NameAndType_NameAndReference(
                goalDefinitionRegistration.getZone(),
                goalDefinitionRegistration.getType(),
                goalDefinitionRegistration.getReference()
        );

        var result = found.orElseGet(() -> {
            var parent = goalDefinitionRegistration.getParent();
            var registeredParent = parent == null ? null : findOrCreate(parent);

            var goalDefinition = goalDefinitionMapper.toEntityFromRegistration(goalDefinitionRegistration);
            goalDefinition.setParent(registeredParent);
            var type = goalTypeRepository.findByName(goalDefinitionRegistration.getType()).orElseThrow();
            var zone = goalZoneRepository.findByName(goalDefinitionRegistration.getZone()).orElseThrow();
            goalDefinition.setType(type);
            goalDefinition.setZone(zone);
            var savedGoalDefinition = goalDefinitionRepository.save(goalDefinition);
            log.info("GoalDefinition registered successfully: id={}", savedGoalDefinition.getId());
            return savedGoalDefinition;
        });
        return result;
    }

    @Override
    public GoalDefinitionDto register(GoalDefinitionRegistrationDto goalDefinitionRegistration) {
        var registered = findOrCreate(goalDefinitionRegistration);
        return goalDefinitionMapper.toDto(registered);
    }
}