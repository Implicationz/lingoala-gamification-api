package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.ObjectiveDefinition;

public record GoalObjectiveMatch(Goal goal, ObjectiveDefinition objectiveDefinition) {}
