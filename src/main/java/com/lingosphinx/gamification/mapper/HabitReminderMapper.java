package com.lingosphinx.gamification.mapper;

import com.lingosphinx.gamification.domain.HabitReminder;
import com.lingosphinx.gamification.dto.HabitReminderDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HabitReminderMapper {
    HabitReminderDto toDto(HabitReminder entity);
    HabitReminder toEntity(HabitReminderDto dto);
    

    void toEntityFromDto(HabitReminderDto goalDto, @MappingTarget  HabitReminder existingHabitReminder);
}