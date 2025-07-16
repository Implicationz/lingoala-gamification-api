package com.lingosphinx.notification.service;

import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.service.UserService;
import com.lingosphinx.notification.domain.NotificationJob;
import com.lingosphinx.notification.dto.NotificationJobDto;
import com.lingosphinx.notification.event.NotificationJobProcessingStartedEvent;
import com.lingosphinx.notification.mapper.NotificationJobMapper;
import com.lingosphinx.notification.repository.NotificationJobRepository;
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
public class NotificationJobServiceImpl implements NotificationJobService {

    private final NotificationJobRepository notificationJobRepository;
    private final NotificationJobMapper notificationJobMapper;
    private final ReceiverRepository receiverRepository;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @Override
    public NotificationJobDto create(NotificationJobDto notificationJobDto) {
        var notificationJob = this.notificationJobMapper.toEntity(notificationJobDto);
        var savedNotificationJob = notificationJobRepository.save(notificationJob);
        log.info("NotificationJob created successfully: id={}", savedNotificationJob.getId());
        return notificationJobMapper.toDto(savedNotificationJob);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationJobDto readById(Long id) {
        var notificationJob = notificationJobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NotificationJob not found"));
        log.info("NotificationJob read successfully: id={}", id);
        return notificationJobMapper.toDto(notificationJob);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationJobDto> readAll() {
        var result = notificationJobRepository.findAll().stream()
                .map(notificationJobMapper::toDto)
                .toList();
        log.info("All notificationJobs read successfully, count={}", result.size());
        return result;
    }

    @Override
    public NotificationJobDto update(Long id, NotificationJobDto notificationJobDto) {
        var existingNotificationJob = notificationJobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NotificationJob not found"));

        notificationJobMapper.toEntityFromDto(notificationJobDto, existingNotificationJob);
        log.info("NotificationJob updated successfully: id={}", existingNotificationJob.getId());
        return notificationJobMapper.toDto(existingNotificationJob);
    }

    @Override
    public void delete(Long id) {
        notificationJobRepository.deleteById(id);
        log.info("NotificationJob deleted successfully: id={}", id);
    }

    @Override
    public void processAll() {
        log.info("NotificationJob processing started");
        this.publisher.publishEvent(new NotificationJobProcessingStartedEvent());
    }
}