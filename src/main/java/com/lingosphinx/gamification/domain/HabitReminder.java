package com.lingosphinx.gamification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;

@BatchSize(size=50)
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HabitReminder extends BaseEntity {

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