package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.user.converters.NotificationRequestDTOToNotificationConverter;
import com.tcc5.car_price_compare.domain.user.converters.NotificationToNotificationResponseDTOConverter;
import com.tcc5.car_price_compare.domain.user.dto.NotificationRequestDTO;
import com.tcc5.car_price_compare.domain.user.dto.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRequestDTOToNotificationConverter notificationRequestDtoToNotificationConverter;
    private final NotificationToNotificationResponseDTOConverter notificationToNotificationResponseDTOConverter;

    public NotificationController(NotificationService notificationService,
            NotificationRequestDTOToNotificationConverter notificationRequestDtoToNotificationConverter,
            NotificationToNotificationResponseDTOConverter notificationToNotificationResponseDTOConverter) {
        this.notificationService = notificationService;
        this.notificationRequestDtoToNotificationConverter = notificationRequestDtoToNotificationConverter;
        this.notificationToNotificationResponseDTOConverter = notificationToNotificationResponseDTOConverter;
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody @Valid NotificationRequestDTO notificationRequestDto) {
        Notification notification = this.notificationRequestDtoToNotificationConverter.convert(notificationRequestDto);
        NotificationResponseDTO notificationResponseDTO = this.notificationToNotificationResponseDTOConverter
                .convert(this.notificationService.save(notification));

        return ResponseEntity.ok(notificationResponseDTO);
    }
}
