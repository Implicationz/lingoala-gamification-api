package com.lingosphinx.notification.mapper;

import com.lingosphinx.notification.domain.NotificationChannel;
import com.lingosphinx.notification.dto.NotificationChannelDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationChannelMapper {
    NotificationChannelDto toDto(NotificationChannel entity);
    NotificationChannel toEntity(NotificationChannelDto dto);

    void toEntityFromDto(NotificationChannelDto dto, @MappingTarget NotificationChannel entity);
}