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
            @RequestParam(value = "notificationStatus", required = false) Integer notificationStatus
    ) {
        Page<NotificationResponseDTO> notifications = this.notificationService.findAll(pageNumber, pageSize, notificationStatus)
                .map(this.conversionService::convertToNotificationResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(notifications);
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
