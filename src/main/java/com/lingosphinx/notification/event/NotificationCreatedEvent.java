package com.lingosphinx.notification.event;

import com.lingosphinx.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationCreatedEvent{

    private final Notification notification;
}
