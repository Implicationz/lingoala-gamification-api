package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.JourneyEntryType;
import lombok.*;

import java.time.Instant;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JourneyEntryDto {

    private Long id;
    private JourneyDto journey;
    private JourneyEntryType type;
    private JourneyItemDto item;
    @Builder.Default
    private Instant timestamp = Instant.now();
}