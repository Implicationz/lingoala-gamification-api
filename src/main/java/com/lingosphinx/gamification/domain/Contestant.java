package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
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

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "contestant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContestantExperience> experiences = new ArrayList<>();

}