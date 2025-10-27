package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.ExperienceValue;
import com.lingosphinx.gamification.domain.GoalZone;

public interface ExperienceProgressService {

    void progress(Contestant contestant, GoalZone zone, ExperienceValue experience);
}
