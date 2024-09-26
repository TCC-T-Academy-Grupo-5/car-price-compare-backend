package com.tcc5.car_price_compare.domain.vehicle;

import com.tcc5.car_price_compare.domain.shared.TimestampedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "year")
public class Year extends TimestampedEntity {

    @Id
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String urlPathName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @OneToOne(mappedBy = "year", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vehicle vehicle;
}
