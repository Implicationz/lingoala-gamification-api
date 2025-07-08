package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long habitId;
    private String fcmToken;
    private String title;
    private String body;

    @Builder.Default
    private boolean sent = false;

    @Builder.Default
    private Instant createdAt = Instant.now();
    private Instant sentAt;
}