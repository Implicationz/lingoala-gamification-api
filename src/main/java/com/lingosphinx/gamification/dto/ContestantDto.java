package com.lingosphinx.gamification.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContestantDto {

    private Long id;
    private UUID userId;
    private List<ContestantExperienceDto> experiences = new ArrayList<>();

}