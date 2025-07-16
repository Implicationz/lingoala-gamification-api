package com.lingosphinx.notification.event;

import com.lingosphinx.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@AllArgsConstructor
public class NotificationCreatedEvent{

    private final Notification notification;
}
