package com.lingosphinx.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.lingosphinx.notification.domain.NotificationChannelType;
import com.lingosphinx.notification.domain.NotificationJob;
import com.lingosphinx.notification.domain.NotificationJobStatus;
import com.lingosphinx.notification.event.NotificationJobProcessingStartedEvent;
import com.lingosphinx.notification.repository.NotificationJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseCloudMessagingServiceImpl {

    private final NotificationJobRepository notificationJobRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Async
    @EventListener
    @Transactional
    public void handleNotificationJobProcessingStarted(NotificationJobProcessingStartedEvent event) {
        var notificationJobs = notificationJobRepository.findAll();
        notificationJobs.stream()
                .filter(job -> job.getChannel().getType() == NotificationChannelType.FCM)
                .forEach(this::send);
    }

    @Retryable(
        retryFor = Exception.class,
        maxAttempts = 5,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    @Transactional
    public void send(NotificationJob job) {
        job.setTrialCount(job.getTrialCount() + 1);
        try {
            var notification = job.getNotification();
            var token = job.getChannel().getToken();

            job.setStatus(NotificationJobStatus.SENT);

            var message = Message.builder()
                    .setToken(token)
                    .setNotification(com.google.firebase.messaging.Notification.builder()
                            .setTitle(notification.getTitle())
                            .setBody(notification.getMessage())
                            .build())
                    .putData("deepLink", "/practice/today")
                    .build();

            firebaseMessaging.send(message);
            job.setStatus(NotificationJobStatus.SENT);
        } catch (Exception e) {
            log.error("FCM error for job {}: {}", job.getId(), e.getMessage());
            job.setStatus(NotificationJobStatus.ERROR);
        }
        notificationJobRepository.save(job);
    }

    @Recover
    @Transactional
    public void recover(Exception e, NotificationJob job) {
        log.error("FCM send failed for job {} after {} trials: {}", job.getId(), job.getTrialCount(), e.getMessage());
        job.setStatus(NotificationJobStatus.FAILED);
        notificationJobRepository.save(job);
    }
}