package com.lingosphinx.gamification.domain;

import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Objective {

    @ManyToOne(optional = false)
    private Goal parent;

    @ManyToOne(optional = false)
    private Goal child;
}