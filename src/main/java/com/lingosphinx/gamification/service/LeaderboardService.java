package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.ExperienceProgress;
import com.lingosphinx.gamification.dto.LeaderboardDto;
import com.lingosphinx.gamification.dto.LeaderboardSearch;
import jakarta.validation.Valid;

public interface LeaderboardService extends CrudService<LeaderboardDto> {

    LeaderboardSearch search(@Valid LeaderboardSearch leaderboardSearch);

    void progress(ExperienceProgress progress);
}
