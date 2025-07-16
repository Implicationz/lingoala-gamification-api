package com.lingosphinx.notification.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NotificationDto {

    private Long id;
    private UUID receiver;
    String title;
    String message;
    private List<NotificationJobDto> jobs = new ArrayList<>();

}