package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.ScoreDefinitionDto;

import java.util.List;

public interface ScoreDefinitionService {
    ScoreDefinitionDto create(ScoreDefinitionDto scoreDefinition);
    ScoreDefinitionDto readById(Long id);
    List<ScoreDefinitionDto> readAll();
    ScoreDefinitionDto update(Long id, ScoreDefinitionDto scoreDefinition);
    void delete(Long id);
}
