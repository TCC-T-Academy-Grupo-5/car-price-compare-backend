package com.tcc5.car_price_compare.domain.price;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "fipe_price")
@Entity(name = "FipePrice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FipePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double price;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "fipe_month_reference_id")
    @JsonIgnore
    private FipeMonthReference fipeMonthReference;
}
