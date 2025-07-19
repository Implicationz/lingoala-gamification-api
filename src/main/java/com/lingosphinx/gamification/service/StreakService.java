package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.domain.Streak;
import com.lingosphinx.gamification.dto.StreakDto;

import java.util.List;

public interface StreakService {
    StreakDto create(StreakDto habit);

    StreakDto readById(Long id);
    List<StreakDto> readAll();
    StreakDto update(Long id, StreakDto habit);
    void delete(Long id);

    void progress(Streak streak);
}
