package com.tcc5.car_price_compare.domain.user.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc5.car_price_compare.domain.user.User;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "rating")
@Entity(name = "rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String message;
    private int rate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private Vehicle vehicle;
}
