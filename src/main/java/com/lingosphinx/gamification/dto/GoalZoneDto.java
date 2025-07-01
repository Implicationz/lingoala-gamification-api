package com.lingosphinx.gamification.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalZoneDto {
    private Long id;
    private String name = "";
}