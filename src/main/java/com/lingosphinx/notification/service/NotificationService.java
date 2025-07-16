package com.lingosphinx.notification.service;
import com.lingosphinx.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto create(NotificationDto notification);
    NotificationDto readById(Long id);
    List<NotificationDto> readAll();
    NotificationDto update(Long id, NotificationDto notification);
    void delete(Long id);
}
