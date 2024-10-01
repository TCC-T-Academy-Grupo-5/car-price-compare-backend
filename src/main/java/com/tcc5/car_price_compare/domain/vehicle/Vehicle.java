package com.tcc5.car_price_compare.domain.vehicle;

import com.tcc5.car_price_compare.domain.price.FipePrice;
import com.tcc5.car_price_compare.domain.shared.GenericTimestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
public class Vehicle extends GenericTimestamp {

    @Id
    private String id;

    @NotBlank
    private String fipeCode;

    @OneToOne
    @JoinColumn(name = "year_id")
    private Year year;

    @OneToOne
    @JoinColumn(name = "price")
    private FipePrice fipePrice;
}
