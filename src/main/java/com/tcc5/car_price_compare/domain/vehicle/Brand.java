package com.tcc5.car_price_compare.domain.vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
public class Brand extends TimestampedEntity {

    @Id
    private UUID id;

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @NotBlank
    @Column(name = "url_path_name", unique = true)
    private String urlPathName;

    private String imageUrl;

    @Enumerated(EnumType.ORDINAL)
    private VehicleType vehicleType;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Model> models = new ArrayList<>();
}
