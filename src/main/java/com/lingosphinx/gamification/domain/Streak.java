package com.lingosphinx.gamification.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RenewalType renewalType = RenewalType.NEVER;

    public void advance() {
    }
}
