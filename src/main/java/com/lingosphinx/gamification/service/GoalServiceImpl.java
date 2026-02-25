package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.*;
import com.lingosphinx.gamification.dto.GoalActivationDto;
import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.event.GoalActivatedEvent;
import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.mapper.GoalMapper;
import com.lingosphinx.gamification.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalDefinitionRepository goalDefinitionRepository;
    private final ObjectiveDefinitionRepository objectiveDefinitionRepository;
    private final GoalMapper goalMapper;
    private final ContestantService contestantService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public GoalDto create(GoalDto goalDto) {
        var goal = goalMapper.toEntity(goalDto);
        var savedGoal = goalRepository.save(goal);
        log.info("Goal created successfully: id={}", savedGoal.getId());
        return goalMapper.toDto(savedGoal);
    }

    @Override
    public GoalDto createByCurrentContestant(GoalDto goalDto) {
        var goal = goalMapper.toEntity(goalDto);
        var contestant = contestantService.readCurrentContestant();
        goal.setContestant(contestant);
        var savedGoal = goalRepository.save(goal);
        log.info("Goal created successfully for user={}: id={}",  contestant.getUserId(), savedGoal.getId());
        return goalMapper.toDto(savedGoal);
    }

    protected void readObjectives(Goal goal) {
        var spec = GoalSpecifications.byContestant(goal.getContestant())
                .and(GoalSpecifications.byParentGoalDefinition(goal.getDefinition()));
        var goals = goalRepository.findAll(spec);
        var objectives = goals.stream()
                .map(g -> Objective.builder().child(g).build())
                .toList();
        goal.setObjectives(objectives);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalDto readById(Long id) {
        var goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        readObjectives(goal);
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
        readObjectives(goal);
        log.info("Goal read by type and reference: type={}, reference={}, id={}", type, reference, goal.getId());
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalDto> search(GoalSearch search) {
        var zone = search.getZone();
        var type = search.getType();
        var contestant = contestantService.readCurrentContestant();
        var spec = GoalSpecifications.byZoneNameAndTypeName(zone, type);

        var references = search.getReferences();
        if(references != null && !references.isEmpty()) {
            spec = spec.and(GoalSpecifications.byReferences(search.getReferences()));
        }

        var isCompleted = search.getIsCompleted();
        if(isCompleted != null) {
            spec = spec.and(isCompleted ? GoalSpecifications.isCompleted() : GoalSpecifications.isNotCompleted());
        }

        var goals = goalRepository.findAll(spec);
        var definitions = goals.stream().map(Goal::getDefinition).toList();
        var objectives = goalRepository.findChildObjectives(definitions, contestant);

        var objectivesByParent = objectives.stream()
                .collect(Collectors.groupingBy(match -> match
                        .objectiveDefinition().getParent().getId()));

        for (var goal : goals) {
            var goalObjectives = objectivesByParent.getOrDefault(goal.getDefinition().getId(), List.of());
            goal.setObjectives(goalObjectives.stream()
                    .map(childGoal -> Objective.builder().child(childGoal.goal()).build())
                    .toList());
        }

        log.info("Goals read by zone and type: zone={}, type={}, count={}", zone, type, goals.size());
        return goals.stream().map(goalMapper::toDto).toList();
    }

    @Override
    public GoalDto activate(GoalActivationDto goalActivation) {
        var contestant = contestantService.readCurrentContestant();
        var spec = GoalSpecifications.byZoneTypeNameAndReferenceAndContestant(goalActivation.getZone(), goalActivation.getType(), goalActivation.getReference(), contestant);
        var found = goalRepository.findOne(spec);
        var activated = found.orElseGet(() -> {
            var definition = goalDefinitionRepository.findByZone_NameAndType_NameAndReference(goalActivation.getZone(),
                            goalActivation.getType(),
                            goalActivation.getReference())
                    .orElseThrow();

            var savedGoal = this.activate(definition, contestant);
            return savedGoal;
        });
        return this.goalMapper.toDto(activated);
    }

    public Goal activate(GoalDefinition definition, Contestant contestant) {
        var goal = Goal.fromDefinition(definition);
        goal.setContestant(contestant);

        var savedGoal = goalRepository.save(goal);
        eventPublisher.publishEvent(new GoalActivatedEvent(definition, contestant));
        log.info("Goal activated successfully: id={}", savedGoal.getId());
        return savedGoal;
    }

    @Override
    public void activateParents(GoalDefinition definition, Contestant contestant) {
        var parents = objectiveDefinitionRepository
                .findAll(ObjectiveDefinitionSpecifications.parentWithoutGoalForContestant(definition, contestant))
                .stream()
                .map(ObjectiveDefinition::getParent)
                .toList();

        for (var parent : parents) {
            activate(parent, contestant);
        }
    }
}