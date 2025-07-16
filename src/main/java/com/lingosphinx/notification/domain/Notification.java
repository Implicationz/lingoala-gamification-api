package com.lingosphinx.notification.domain;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.GoalType;
import com.lingosphinx.gamification.domain.Progress;
import com.lingosphinx.gamification.domain.ProgressValue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    private Receiver receiver;

    String title;
    String message;

    @Builder.Default
    @BatchSize(size=50)
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationJob> jobs = new ArrayList<>();

}