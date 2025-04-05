package com.lockdown.lockmanagement.services;

import com.lockdown.lockmanagement.TestContainers;
import com.lockdown.lockmanagement.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConfiguration(proxyBeanMethods = false)
@ImportTestcontainers(TestContainers.class)
class OrdersServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should save only one order when multiple concurrent booking attempts are made")
    void shouldSaveOnlyOneOrderWhenConcurrentBookingsAttempted() throws InterruptedException {
        final var court = "BANA 1";
        final var booking = "10:00";
        final int threadCount = 50;

        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        orderService.saveOrder(court, booking);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executor.shutdown();
        }

        assertEquals(1, orderRepository.findAll().size());
    }
}