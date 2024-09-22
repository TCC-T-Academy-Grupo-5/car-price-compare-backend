package com.tcc5.car_price_compare.domain.price;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Table(name = "fipe_month_reference")
@Entity(name = "FipeMonthReference")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FipeMonthReference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;

    private String month;

    @OneToMany(mappedBy = "fipeMonthReference")
    @JsonIgnore
    private List<FipePrice> fipePrices;
}
