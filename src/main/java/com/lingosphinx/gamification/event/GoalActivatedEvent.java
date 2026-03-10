package com.lingosphinx.gamification.event;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Goal;
import com.lingosphinx.gamification.domain.GoalDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GoalActivatedEvent {
    private final Goal goal;
}
