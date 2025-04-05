package com.lockdown.lockmanagement.repository.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"court", "booking"})
})
public class Orders {

    @Id
    @GeneratedValue
    private UUID id;

    private String court;

    private String booking;

    public Orders(String court, String booking) {
        this.court = court;
        this.booking = booking;
    }

    public Orders(UUID id, String court, String booking) {
        this.id = id;
        this.court = court;
        this.booking = booking;
    }
}
