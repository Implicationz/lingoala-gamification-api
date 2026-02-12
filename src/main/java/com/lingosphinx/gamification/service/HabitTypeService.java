package com.lingosphinx.gamification.service;
import com.lingosphinx.gamification.domain.HabitType;
import com.lingosphinx.gamification.dto.HabitTypeDto;

import java.util.List;

public interface HabitTypeService extends CrudService<HabitTypeDto> {
    HabitTypeDto readByName(String name);
}
