package com.tcc5.car_price_compare.domain.user.features;

import com.tcc5.car_price_compare.domain.shared.TimestampedEntity;
import com.tcc5.car_price_compare.domain.user.enums.NotificationStatus;
import com.tcc5.car_price_compare.domain.user.enums.NotificationType;
import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Table(name = "notification", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "vehicle_id", "notification_status", "notification_type"},
                          name = "unique_user_vehicle_status_type_notification")
})
@Entity(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Notification extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private NotificationType notificationType;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private NotificationStatus notificationStatus;

    @NotNull
    private Double currentFipePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
