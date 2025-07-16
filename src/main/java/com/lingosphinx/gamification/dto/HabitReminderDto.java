package com.lingosphinx.gamification.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitReminderDto {

    private Long id;

    private Long habitId;
    private String fcmToken;
    private String title;
    private String body;

    @Builder.Default
    private boolean sent = false;
    @Builder.Default
    private int trialCount = 0;

    @Builder.Default
    private Instant createdAt = Instant.now();
    private Instant sentAt;
}