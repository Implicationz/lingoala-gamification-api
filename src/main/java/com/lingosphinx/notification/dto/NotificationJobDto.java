package com.lingosphinx.notification.dto;

import com.lingosphinx.notification.domain.Notification;
import com.lingosphinx.notification.domain.NotificationChannel;
import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NotificationJobDto {

    private Long id;
    private NotificationDto notification;
    private NotificationChannelDto channel;

}