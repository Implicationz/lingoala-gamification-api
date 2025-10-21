package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.GoalSearch;
import com.lingosphinx.gamification.dto.GoalActivationDto;
import com.lingosphinx.gamification.dto.GoalDto;
import jakarta.validation.Valid;

import java.util.List;

public interface GoalService {
    GoalDto create(GoalDto goal);

    GoalDto createByCurrentContestant(GoalDto goalDto);

    GoalDto readById(Long id);
    List<GoalDto> readAll();
    GoalDto update(Long id, GoalDto goal);
    void delete(Long id);

    GoalDto readByTypeNameAndReference(String type, String reference);

    GoalDto activate(@Valid GoalActivationDto goalDefinitionActivation);

    void activateParents(GoalDefinition definition, Contestant contestant);

    List<GoalDto> search(GoalSearch search);
}
