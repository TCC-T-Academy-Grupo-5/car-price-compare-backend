package com.tcc5.car_price_compare.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tcc5.car_price_compare.domain.shared.TimestampedEntity;
import com.tcc5.car_price_compare.domain.vehicle.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
public class Brand extends TimestampedEntity {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String urlPathName;

    @Enumerated(EnumType.ORDINAL)
    private VehicleType vehicleType;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Model> models = new ArrayList<>();
}
