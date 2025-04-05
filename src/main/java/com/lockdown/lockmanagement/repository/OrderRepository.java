package com.lockdown.lockmanagement.repository;

import com.lockdown.lockmanagement.repository.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {

    Optional<Orders> findByCourtAndTime(String court, String time);
}
