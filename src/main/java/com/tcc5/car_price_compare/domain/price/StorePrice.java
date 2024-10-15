package com.tcc5.car_price_compare.domain.price;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc5.car_price_compare.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "store_price")
@Entity(name = "StorePrice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class StorePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String store;

    private Double price;

    private Double mileageInKm;

    private String modelName;

    private String versionName;

    private String year;

    private String dealUrl;

    private String imageUrl;

    private Boolean isFullMatch;

    private String city;

    private String state;

    @Column(name = "scrapping_date")
    private LocalDateTime scrappingDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private Vehicle vehicle;

}
