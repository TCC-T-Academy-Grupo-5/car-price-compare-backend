package com.tcc5.car_price_compare.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Table(name = "year")
@Entity(name = "Year")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Year {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String year;
    private String code;

    @ManyToOne()
    @JoinColumn(name = "model_id")
    @JsonIgnore
    private Model model;

    @OneToMany(mappedBy = "year")
    private List<Vehicle> vehicles;
}
