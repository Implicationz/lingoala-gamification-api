package com.lingosphinx.notification.dto;

import com.lingosphinx.notification.domain.Notification;
import com.lingosphinx.notification.domain.NotificationChannelType;
import com.lingosphinx.notification.domain.Receiver;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NotificationChannelDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationChannelType type = NotificationChannelType.FCM;

    @ManyToOne(optional = false)
    private Receiver receiver;

    @Column(nullable = false)
    private String token;

    @Builder.Default
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notification = new ArrayList<>();

}