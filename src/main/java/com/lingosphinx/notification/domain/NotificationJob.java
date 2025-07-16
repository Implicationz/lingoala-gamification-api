package com.lingosphinx.notification.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NotificationJob {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    private Notification notification;
    
    @ManyToOne(optional = false)
    private NotificationChannel channel;

    private int trialCount = 0;
    private NotificationJobStatus status = NotificationJobStatus.CREATED;

}