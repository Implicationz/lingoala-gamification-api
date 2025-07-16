package com.lingosphinx.notification.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NotificationChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationChannelType type = NotificationChannelType.FCM;

    @ManyToOne(optional = false)
    private Receiver receiver;

    @Column(nullable = false)
    private String token;
}