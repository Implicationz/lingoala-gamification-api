package com.lingosphinx.notification.service;

import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.service.UserService;
import com.lingosphinx.notification.domain.NotificationJob;
import com.lingosphinx.notification.dto.NotificationDto;
import com.lingosphinx.notification.event.NotificationCreatedEvent;
import com.lingosphinx.notification.mapper.NotificationMapper;
import com.lingosphinx.notification.repository.NotificationRepository;
import com.lingosphinx.notification.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ReceiverRepository receiverRepository;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @Override
    public NotificationDto create(NotificationDto notificationDto) {
        var userId = notificationDto.getReceiver();
        var receiver = receiverRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found for userId=" + userId));

        var notification = notificationMapper.toEntity(notificationDto);
        notification.setReceiver(receiver);

        var jobs = receiver.getChannels().stream()
                .map(channel -> NotificationJob.builder()
                        .channel(channel)
                        .notification(notification)
                        .build())
                .toList();

        notification.setJobs(jobs);

        var savedNotification = notificationRepository.save(notification);
        log.info("Notification created successfully: id={}", savedNotification.getId());

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        publisher.publishEvent(new NotificationCreatedEvent(notification));
                    }
                }
        );

        return notificationMapper.toDto(savedNotification);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationDto readById(Long id) {
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        log.info("Notification read successfully: id={}", id);
        return notificationMapper.toDto(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> readAll() {
        var result = notificationRepository.findAll().stream()
                .map(notificationMapper::toDto)
                .toList();
        log.info("All notifications read successfully, count={}", result.size());
        return result;
    }

    @Override
    public NotificationDto update(Long id, NotificationDto notificationDto) {
        var existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notificationMapper.toEntityFromDto(notificationDto, existingNotification);
        log.info("Notification updated successfully: id={}", existingNotification.getId());
        return notificationMapper.toDto(existingNotification);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
        log.info("Notification deleted successfully: id={}", id);
    }
}