package com.tcc5.car_price_compare.domain.statistic;

import com.tcc5.car_price_compare.domain.statistic.enums.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "statistic")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityType entityType;

    @Column(nullable = false)
    private UUID entityId;

    @Column(nullable = false)
    private Long searchCount = 0L;

    public void incrementSearchCount() {
        this.searchCount++;
    }
}
