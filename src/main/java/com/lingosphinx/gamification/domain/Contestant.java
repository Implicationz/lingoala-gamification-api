package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_contestant_user_id", columnList = "userId"),
                @Index(name = "idx_contestant_time_zone", columnList = "timeZone")
        }
)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contestant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    private IanaTimeZone timeZone;

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "contestant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContestantExperience> experiences = new ArrayList<>();

}