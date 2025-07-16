package com.lingosphinx.notification.domain;

import com.lingosphinx.gamification.domain.GoalDefinition;
import com.lingosphinx.gamification.domain.GoalType;
import com.lingosphinx.gamification.domain.Progress;
import com.lingosphinx.gamification.domain.ProgressValue;
import jakarta.persistence.*;
import lombok.*;

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
@Table(
        indexes = {
                @Index(name = "idx_receiver_user_id", columnList = "user_id")
        }
)
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Builder.Default
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationChannel> channels = new ArrayList<>();
}