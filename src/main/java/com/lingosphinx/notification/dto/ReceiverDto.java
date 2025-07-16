package com.lingosphinx.notification.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReceiverDto {

    private Long id;
    private UUID userId;
    private List<NotificationChannelDto> channels = new ArrayList<>();
}