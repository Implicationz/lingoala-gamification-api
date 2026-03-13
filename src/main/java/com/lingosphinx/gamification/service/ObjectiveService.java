package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.domain.*;
import com.lingosphinx.gamification.dto.GoalActivationDto;
import com.lingosphinx.gamification.dto.GoalDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ObjectiveService {

    void activateParents(Goal goal);
    void propagate(GoalProgress goalProgress);

    void propagate(Objective objective, GoalProgress goalProgress);
}
