package com.lingosphinx.notification.service;

import com.lingosphinx.gamification.exception.ResourceNotFoundException;
import com.lingosphinx.gamification.service.UserService;
import com.lingosphinx.notification.domain.Receiver;
import com.lingosphinx.notification.dto.NotificationChannelDto;
import com.lingosphinx.notification.mapper.NotificationChannelMapper;
import com.lingosphinx.notification.repository.NotificationChannelRepository;
import com.lingosphinx.notification.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationChannelImpl implements NotificationChannelService {

    private final NotificationChannelRepository notificationChannelRepository;
    private final NotificationChannelMapper notificationChannelMapper;
    private final UserService userService;
    private final ReceiverRepository receiverRepository;

    @Override
    public NotificationChannelDto create(NotificationChannelDto notificationChannelDto) {
        var notificationChannel = notificationChannelMapper.toEntity(notificationChannelDto);
        var savedNotificationChannel = notificationChannelRepository.save(notificationChannel);
        log.info("NotificationChannel created successfully: id={}", savedNotificationChannel.getId());
        return notificationChannelMapper.toDto(savedNotificationChannel);
    }

    @Override
    public NotificationChannelDto createByCurrentUser(NotificationChannelDto notificationChannelDto) {
        var notificationChannel = notificationChannelMapper.toEntity(notificationChannelDto);
        var userId = userService.getCurrentUserId();
        var receiver = receiverRepository.findByUserId(userId)
                .orElseGet(() -> {
                    var newReceiver = new Receiver();
                    newReceiver.setUserId(userId);
                    return receiverRepository.save(newReceiver);
                });
        notificationChannel.setReceiver(receiver);
        var savedNotificationChannel = notificationChannelRepository.save(notificationChannel);
        log.info("NotificationChannel created successfully for user={}: id={}", userId, savedNotificationChannel.getId());
        return notificationChannelMapper.toDto(savedNotificationChannel);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationChannelDto readById(Long id) {
        var notificationChannel = notificationChannelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NotificationChannel not found"));
        log.info("NotificationChannel read successfully: id={}", id);
        return notificationChannelMapper.toDto(notificationChannel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationChannelDto> readAll() {
        var result = notificationChannelRepository.findAll().stream()
                .map(notificationChannelMapper::toDto)
                .toList();
        log.info("All notificationChannels read successfully, count={}", result.size());
        return result;
    }

    @Override
    public NotificationChannelDto update(Long id, NotificationChannelDto notificationChannelDto) {
        var existingNotificationChannel = notificationChannelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NotificationChannel not found"));

        notificationChannelMapper.toEntityFromDto(notificationChannelDto, existingNotificationChannel);
        log.info("NotificationChannel updated successfully: id={}", existingNotificationChannel.getId());
        return notificationChannelMapper.toDto(existingNotificationChannel);
    }

    @Override
    public void delete(Long id) {
        notificationChannelRepository.deleteById(id);
        log.info("NotificationChannel deleted successfully: id={}", id);
    }
}