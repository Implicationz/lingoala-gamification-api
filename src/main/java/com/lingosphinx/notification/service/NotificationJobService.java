package com.lingosphinx.notification.service;
import com.lingosphinx.notification.dto.NotificationJobDto;

import java.util.List;

public interface NotificationJobService {
    NotificationJobDto create(NotificationJobDto notificationJob);
    NotificationJobDto readById(Long id);
    List<NotificationJobDto> readAll();
    NotificationJobDto update(Long id, NotificationJobDto notificationJob);
    void delete(Long id);

    void processAll();
}
