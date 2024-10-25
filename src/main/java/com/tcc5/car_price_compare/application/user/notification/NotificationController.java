package com.tcc5.car_price_compare.application.user.notification;

import com.tcc5.car_price_compare.application.ConversionService;
import com.tcc5.car_price_compare.domain.request.user.NotificationRequestDTO;
import com.tcc5.car_price_compare.domain.response.user.NotificationResponseDTO;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import com.tcc5.car_price_compare.shared.utils.PaginationHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user/notifications")
@Tag(name = "Notifications", description = "Endpoints for managing user notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ConversionService conversionService;

    public NotificationController(NotificationService notificationService, ConversionService conversionService) {
        this.notificationService = notificationService;
        this.conversionService = conversionService;
    }

    @GetMapping
    @Operation(summary = "Get all notifications", description = "This endpoint retrieves a paginated list of all notifications, optionally filtered by status.")
    public ResponseEntity<List<NotificationResponseDTO>> getNotifications(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        pageNumber = Math.max(1, pageNumber);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<NotificationResponseDTO> notifications = this.notificationService.findAll(pageable, status)
                .map(this.conversionService::convertToNotificationResponseDTO);
        HttpHeaders headers = PaginationHeaders.createPaginationHeaders(notifications);

        return ResponseEntity.ok().headers(headers).body(notifications.getContent());
    }

    @GetMapping("/{notificationId}")
    @Operation(summary = "Get notification by ID", description = "This endpoint retrieves a notification by its ID.")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(@PathVariable UUID notificationId) {
        NotificationResponseDTO notificationResponseDTO = this.conversionService
                .convertToNotificationResponseDTO(this.notificationService.findById(notificationId));
        return ResponseEntity.status(HttpStatus.OK).body(notificationResponseDTO);
    }

    @GetMapping("/existsPendingByVehicleId/{vehicleId}")
    @Operation(summary = "Check if notification exists by vehicle ID", description = "This endpoint checks if there is a pending notification for a vehicle by its ID.")
    public ResponseEntity<Map<String, String>> existsByVehicleId(@PathVariable UUID vehicleId) {
        Notification notification = this.notificationService.findPendingByVehicleId(vehicleId);
        boolean exists = notification != null;
        Map<String, String> response = new HashMap<>();
        response.put("exists", String.valueOf(exists));
        if (exists) {
            response.put("notificationId", notification.getId().toString());
        }

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Add notification", description = "This endpoint adds a new notification.")
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody @Valid NotificationRequestDTO notificationRequestDto) {
        Notification notification = this.conversionService.convertToNotificationEntity(notificationRequestDto);
        NotificationResponseDTO notificationResponseDTO = this.conversionService
                .convertToNotificationResponseDTO(this.notificationService.save(notification));

        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResponseDTO);
    }

    @PutMapping("/{notificationId}")
    @Operation(summary = "Update notification", description = "This endpoint updates a notification.")
    public ResponseEntity<NotificationResponseDTO> updateFavorite(@PathVariable UUID notificationId,
            @RequestBody @Valid NotificationRequestDTO notificationRequestDto) {
        Notification update = this.conversionService.convertToNotificationEntity(notificationRequestDto);
        Notification updatedNotification = this.notificationService.update(notificationId, update);
        NotificationResponseDTO notificationResponseDTO = this.conversionService.convertToNotificationResponseDTO(updatedNotification);

        return ResponseEntity.status(HttpStatus.OK).body(notificationResponseDTO);
    }

    @DeleteMapping("/{notificationId}")
    @Operation(summary = "Delete notification", description = "This endpoint deletes a notification.")
    public ResponseEntity<?> deleteNotification(@PathVariable UUID notificationId) {
        this.notificationService.delete(notificationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
