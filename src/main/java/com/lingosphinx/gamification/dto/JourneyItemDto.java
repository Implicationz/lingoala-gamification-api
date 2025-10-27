package com.lingosphinx.gamification.dto;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JourneyItemDto {

    private Long id;
    private JourneyItemTypeDto type;
    private String name;
    private String reference;
}