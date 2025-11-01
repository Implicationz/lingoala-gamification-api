package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contestant extends BaseEntity {

    @Column(nullable = false, unique = true)
    private UUID userId;

    private IanaTimeZone timeZone;

    private String image;
    private String name;

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "contestant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContestantExperience> experiences = new ArrayList<>();

}