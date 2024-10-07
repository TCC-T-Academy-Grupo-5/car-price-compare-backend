package com.tcc5.car_price_compare.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc5.car_price_compare.domain.price.FipePrice;
import com.tcc5.car_price_compare.domain.shared.TimestampedEntity;
import com.tcc5.car_price_compare.domain.user.features.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
public class Vehicle extends TimestampedEntity {

    @Id
    private UUID id;

    @NotBlank
    private String fipeCode;

    @NotBlank
    private String urlPathName;

    private String fullUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "year_id")
    private Year year;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "price")
    private FipePrice fipePrice;
}
