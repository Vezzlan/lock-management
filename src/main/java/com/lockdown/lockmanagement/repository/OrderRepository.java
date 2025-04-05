package com.lockdown.lockmanagement.repository;

import com.lockdown.lockmanagement.repository.entities.Orders;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {

    Optional<Orders> findByCourtAndBooking(String court, String booking);
}
