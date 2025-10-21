package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDefinitionRegistrationDto;
import com.lingosphinx.gamification.dto.ObjectiveDefinitionDto;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.GoalDefinitionMapper;
import com.lingosphinx.gamification.repository.GoalDefinitionRepository;
import com.lingosphinx.gamification.repository.GoalDefinitionSpecifications;
import com.lingosphinx.gamification.repository.GoalTypeRepository;
import com.lingosphinx.gamification.repository.GoalZoneRepository;
import com.lingosphinx.gamification.utility.EntitySyncUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    @Override
    public List<GoalDefinitionDto> readAll(String type, List<String> reference) {
        var spec = GoalDefinitionSpecifications.byTypeAndReferences(type, reference);
        var result = goalDefinitionRepository.findAll(spec).stream()
                .map(goalDefinitionMapper::toDto)
                .toList();
        log.info("Filtered goalDefinitions read successfully, count={}", result.size());
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

        EntitySyncUtils.syncChildEntities(existingGoalDefinition.getObjectives(), goalDefinitionDto.getObjectives(),
            ObjectiveDefinition::getId,
            ObjectiveDefinitionDto::getId,
            (dto) -> {
                var entity = this.goalDefinitionMapper.toEntity(dto);
                var child = this.goalDefinitionRepository.getReferenceById(dto.getChild().getId());
                entity.setChild(child);
                return entity;
            },
            entity -> {
                entity.setParent(existingGoalDefinition);
            },
            (dto, entity) -> {
                goalDefinitionMapper.updateEntityFromDto(dto, entity);
                var child = this.goalDefinitionRepository.getReferenceById(dto.getChild().getId());
                entity.setChild(child);
            }
        );
        goalDefinitionRepository.flush();

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
            var goalDefinition = goalDefinitionMapper.toEntityFromRegistration(goalDefinitionRegistration);
            var type = goalTypeRepository.findByName(goalDefinitionRegistration.getType()).orElseThrow(() -> new ResourceNotFoundException("GoalType not found"));
            var zone = goalZoneRepository.findByName(goalDefinitionRegistration.getZone()).orElseThrow(() -> new ResourceNotFoundException("GoalZone not found"));
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