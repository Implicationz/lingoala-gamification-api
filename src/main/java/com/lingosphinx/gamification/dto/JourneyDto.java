package com.lingosphinx.gamification.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JourneyDto {

    private Long id;
    private GoalZoneDto zone;
    private ContestantDto contestant;
    private List<JourneyEntryDto> entries = new ArrayList<>();

}