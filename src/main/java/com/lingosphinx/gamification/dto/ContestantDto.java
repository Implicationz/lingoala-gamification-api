package com.lingosphinx.gamification.dto;

import com.lingosphinx.gamification.domain.IanaTimeZone;
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
    private IanaTimeZone timeZone;
    private String image;
    private String name;
    private List<ContestantExperienceDto> experiences = new ArrayList<>();

}