package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.JourneyItemCategory;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JourneyItemTypeDto {

    private Long id;
    private JourneyItemCategory category;
    private String name;

}
