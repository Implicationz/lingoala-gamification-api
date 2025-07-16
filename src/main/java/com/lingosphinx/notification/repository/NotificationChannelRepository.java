package com.lingosphinx.notification.repository;

import com.lingosphinx.notification.domain.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long>, JpaSpecificationExecutor<NotificationChannel> {
}
