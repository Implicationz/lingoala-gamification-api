package com.lingosphinx.gamification.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoalSearch {
    private String zone;
    private String type;
    private Boolean isCompleted;
    private List<String> references = new ArrayList<>();
}