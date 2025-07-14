package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.dto.ProgressDto;

import java.util.List;

public interface ProgressService {
    ProgressDto create(ProgressDto progress);
    ProgressDto readById(Long id);
    List<ProgressDto> readAll();
    ProgressDto update(Long id, ProgressDto progress);
    void delete(Long id);
}
