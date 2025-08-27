package com.lingosphinx.gamification.dto;

import lombok.*;

import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDto {

    private Long id;
    private UUID receiver;
    String title;
    String message;
}