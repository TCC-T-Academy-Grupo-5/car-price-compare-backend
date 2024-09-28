package com.tcc5.car_price_compare.domain.shared;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class TimestampedEntity {

    @CreationTimestamp
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    protected LocalDateTime updatedAt;
}
