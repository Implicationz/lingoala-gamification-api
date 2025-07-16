package com.lingosphinx.notification.service;
import com.lingosphinx.notification.dto.NotificationChannelDto;

import java.util.List;

public interface NotificationChannelService {
    NotificationChannelDto create(NotificationChannelDto goal);

    NotificationChannelDto createByCurrentUser(NotificationChannelDto goalDto);

    NotificationChannelDto readById(Long id);
    List<NotificationChannelDto> readAll();
    NotificationChannelDto update(Long id, NotificationChannelDto goal);
    void delete(Long id);
}
