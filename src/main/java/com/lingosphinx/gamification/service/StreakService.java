package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.domain.Streak;
import com.lingosphinx.gamification.dto.StreakDto;

import java.util.List;

public interface StreakService {
    StreakDto create(StreakDto streak);

    StreakDto readById(Long id);
    List<StreakDto> readAll();
    StreakDto update(Long id, StreakDto streak);
    void delete(Long id);

    void progress(Streak streak);
}
