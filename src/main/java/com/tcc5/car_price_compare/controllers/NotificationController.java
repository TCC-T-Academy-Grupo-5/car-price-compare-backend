package com.tcc5.car_price_compare.controllers;

import com.tcc5.car_price_compare.domain.request.user.NotificationRequestDTO;
import com.tcc5.car_price_compare.domain.response.user.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.services.ConversionService;
import com.tcc5.car_price_compare.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ConversionService conversionService;

    public NotificationController(NotificationService notificationService, ConversionService conversionService) {
        this.notificationService = notificationService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<Page<NotificationResponseDTO>> getNotifications(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        Page<NotificationResponseDTO> notifications = this.notificationService.findAll(pageNumber, pageSize, status)
                .map(this.conversionService::convertToNotificationResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(notifications);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(@PathVariable UUID notificationId) {
        NotificationResponseDTO notificationResponseDTO = this.conversionService
                .convertToNotificationResponseDTO(this.notificationService.findById(notificationId));
        return ResponseEntity.status(HttpStatus.OK).body(notificationResponseDTO);
    }


    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody @Valid NotificationRequestDTO notificationRequestDto) {
        Notification notification = this.conversionService.convertToNotificationEntity(notificationRequestDto);
        NotificationResponseDTO notificationResponseDTO = this.conversionService
                .convertToNotificationResponseDTO(this.notificationService.save(notification));

        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResponseDTO);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> updateFavorite(@PathVariable UUID notificationId,
            @RequestBody @Valid NotificationRequestDTO notificationRequestDto) {
        Notification update = this.conversionService.convertToNotificationEntity(notificationRequestDto);
        Notification updatedNotification = this.notificationService.update(notificationId, update);
        NotificationResponseDTO notificationResponseDTO = this.conversionService.convertToNotificationResponseDTO(updatedNotification);

        return ResponseEntity.status(HttpStatus.OK).body(notificationResponseDTO);
    }
}
