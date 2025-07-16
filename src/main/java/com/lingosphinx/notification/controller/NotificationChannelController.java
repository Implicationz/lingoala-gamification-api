package com.lingosphinx.notification.controller;

import com.lingosphinx.notification.dto.NotificationChannelDto;
import com.lingosphinx.notification.service.NotificationChannelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification-channel")
@RequiredArgsConstructor
@Tag(name = "NotificationChannel API")
public class NotificationChannelController {

    private final NotificationChannelService notificationChannelService;

    @PostMapping
    public ResponseEntity<NotificationChannelDto> create(@RequestBody @Valid NotificationChannelDto notificationChannel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationChannelService.createByCurrentUser(notificationChannel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationChannelDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationChannelService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationChannelDto>> readAll() {
        return ResponseEntity.ok(notificationChannelService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationChannelDto> update(@PathVariable Long id, @RequestBody @Valid NotificationChannelDto notificationChannel) {
        return ResponseEntity.ok(notificationChannelService.update(id, notificationChannel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationChannelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}