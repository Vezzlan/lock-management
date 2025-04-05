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
        @UniqueConstraint(columnNames = {"court", "time"})
})
public class Orders {

    @Id
    @GeneratedValue
    private UUID id;

    private String court;

    private String time;

    public Orders(String court, String time) {
        this.court = court;
        this.time= time;
    }

    public Orders(UUID id, String court, String time) {
        this.id = id;
        this.court = court;
        this.time = time;
    }
}
