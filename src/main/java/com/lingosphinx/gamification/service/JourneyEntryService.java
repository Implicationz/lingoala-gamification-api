package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.JourneyEntryDto;
import com.lingosphinx.gamification.event.GoalCompletedEvent;

public interface JourneyEntryService extends CrudService<JourneyEntryDto> {

    void handle(GoalCompletedEvent event);
}
