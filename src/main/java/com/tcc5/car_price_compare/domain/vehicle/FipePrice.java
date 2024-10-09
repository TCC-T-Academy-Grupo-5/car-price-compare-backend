package com.tcc5.car_price_compare.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc5.car_price_compare.domain.price.FipeMonthReference;
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

    private Double price;

    private Integer month;

    private Integer year;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private Vehicle vehicle;
}
