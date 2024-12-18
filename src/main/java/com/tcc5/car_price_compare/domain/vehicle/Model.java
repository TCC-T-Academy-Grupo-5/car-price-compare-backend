package com.tcc5.car_price_compare.domain.vehicle;

import com.tcc5.car_price_compare.domain.shared.TimestampedEntity;
import com.tcc5.car_price_compare.domain.vehicle.enums.ModelCategory;
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
@Table(name = "model")
public class Model extends TimestampedEntity {

    @Id
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String urlPathName;

    private String imageUrl;

    private ModelCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Year> years = new ArrayList<>();
}