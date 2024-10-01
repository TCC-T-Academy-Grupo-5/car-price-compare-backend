package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.user.dto.NotificationRequestDTO;
import com.tcc5.car_price_compare.domain.user.dto.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.services.ConversionService;
import com.tcc5.car_price_compare.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ConversionService conversionService;

    public NotificationController(NotificationService notificationService, ConversionService conversionService) {
        this.notificationService = notificationService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody @Valid NotificationRequestDTO notificationRequestDto) {
        Notification notification = this.conversionService.convertToNotificationEntity(notificationRequestDto);
        NotificationResponseDTO notificationResponseDTO = this.conversionService
                .convertToNotificationResponseDTO(this.notificationService.save(notification));

        // TODO: change status to created with the uri of the new notification when GET notifications is implemented
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResponseDTO);
    }
}
