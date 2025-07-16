package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;

@BatchSize(size=500)
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Habit habit;
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