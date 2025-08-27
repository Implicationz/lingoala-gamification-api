package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.dto.ContestantDto;

import java.util.List;

public interface ContestantService {

    ContestantDto registerCurrent();

    ContestantDto create(ContestantDto contestant);

    ContestantDto readById(Long id);
    List<ContestantDto> readAll();
    ContestantDto update(Long id, ContestantDto contestant);
    void delete(Long id);

    Contestant readCurrentContestant();


}
