package com.tcc5.car_price_compare.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc5.car_price_compare.domain.price.FipePrice;
import com.tcc5.car_price_compare.domain.user.features.Favorites;
import com.tcc5.car_price_compare.domain.user.features.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Table(name = "vehicle")
@Entity(name = "Vehicle")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "fipe_code")
    private String fipeCode;

    @Column(name = "reference_month")
    private String referenceMonth;

    @Enumerated(EnumType.STRING)
    private FuelTypeEnum fuelType;

    @ManyToOne
    @JoinColumn(name = "year_id")
    @JsonIgnore
    private Year releaseYear;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<FipePrice> prices;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<Rating> ratings;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<Favorites> favorites;
}
