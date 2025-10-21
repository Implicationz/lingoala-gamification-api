package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.GoalDefinitionDto;
import com.lingosphinx.gamification.dto.GoalDefinitionRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoalDefinitionService {
    GoalDefinitionDto create(GoalDefinitionDto goalDefinition);
    GoalDefinitionDto readById(Long id);

    @Transactional(readOnly = true)
    List<GoalDefinitionDto> readAll(String type, List<String> reference);

    GoalDefinitionDto update(Long id, GoalDefinitionDto goalDefinition);
    void delete(Long id);

    GoalDefinitionDto readByTypeNameAndReference(String type, String reference);

    GoalDefinitionDto register(@Valid GoalDefinitionRegistrationDto goalDefinitionRegistration);
}
