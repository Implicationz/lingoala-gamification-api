package com.lingosphinx.notification.dto;

import com.lingosphinx.notification.domain.NotificationJob;
import com.lingosphinx.notification.domain.Receiver;
import jakarta.persistence.*;
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