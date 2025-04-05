package com.lockdown.lockmanagement.services;

import com.lockdown.lockmanagement.repository.OrderRepository;
import com.lockdown.lockmanagement.repository.entities.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class OrderService {

    private final Lock lock = new ReentrantLock();

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void saveOrder(String court, String booking) {
        orderRepository.findByCourtAndBooking(court, booking)
                .ifPresentOrElse(
                        (order) -> System.out.println("Order already exists " + order.getCourt() + " " + order.getBooking()),
                        () -> orderRepository.save(generate(court, booking)));
    }

    private Orders generate(String court, String booking) {
        return Orders.builder()
                .court(court)
                .booking(booking)
                .build();
    }
}
