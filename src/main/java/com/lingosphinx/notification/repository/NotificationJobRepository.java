package com.lingosphinx.notification.repository;

import com.lingosphinx.notification.domain.Notification;
import com.lingosphinx.notification.domain.NotificationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotificationJobRepository extends JpaRepository<NotificationJob, Long>, JpaSpecificationExecutor<NotificationJob> {
}
