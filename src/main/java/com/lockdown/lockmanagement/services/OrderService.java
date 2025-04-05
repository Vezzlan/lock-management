package com.lockdown.lockmanagement.services;

import com.lockdown.lockmanagement.repository.OrderRepository;
import com.lockdown.lockmanagement.repository.entities.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void saveOrder(String court, String time) {
        orderRepository.findByCourtAndTime(court, time)
                .ifPresentOrElse(
                        (order) -> System.out.println("Order already exists " + order.getCourt() + " " + order.getTime()),
                        () -> orderRepository.save(generate(court, time)));
    }

    private Orders generate(String court, String time) {
        return Orders.builder()
                .court(court)
                .time(time)
                .build();
    }
}
