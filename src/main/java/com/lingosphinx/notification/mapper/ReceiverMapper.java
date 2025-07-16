package com.lingosphinx.notification.mapper;

import com.lingosphinx.notification.domain.Receiver;
import com.lingosphinx.notification.dto.ReceiverDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReceiverMapper {
    ReceiverDto toDto(Receiver entity);
    Receiver toEntity(ReceiverDto dto);

    void toEntityFromDto(ReceiverDto dto, @MappingTarget Receiver entity);
}