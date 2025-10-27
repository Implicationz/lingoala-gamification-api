package com.lingosphinx.gamification.service;

import com.lingosphinx.gamification.dto.JourneyActivationDto;
import com.lingosphinx.gamification.dto.JourneyDto;

public interface JourneyService extends CrudService<JourneyDto> {

    JourneyActivationDto activate(JourneyActivationDto journeyActivation);
}
