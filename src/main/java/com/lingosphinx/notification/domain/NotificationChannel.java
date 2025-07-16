package com.lingosphinx.notification.domain;

import com.lingosphinx.gamification.domain.ProgressValue;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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