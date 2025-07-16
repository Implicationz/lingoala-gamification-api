package com.lingosphinx.notification.mapper;

import com.lingosphinx.notification.domain.Notification;
import com.lingosphinx.notification.domain.NotificationJob;
import com.lingosphinx.notification.dto.NotificationDto;
import com.lingosphinx.notification.dto.NotificationJobDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationJobMapper {

    NotificationJobDto toDto(NotificationJob entity);
    NotificationJob toEntity(NotificationJobDto dto);

    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    NotificationDto toDto(Notification entity);

    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    Notification toEntity(NotificationDto dto);

    void toEntityFromDto(NotificationJobDto dto, @MappingTarget NotificationJob entity);
}