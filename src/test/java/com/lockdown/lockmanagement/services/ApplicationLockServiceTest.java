package com.lockdown.lockmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ApplicationLockServiceTest {

    private ApplicationLockService applicationLockService;

    @BeforeEach
    void setUp() {
        applicationLockService = new ApplicationLockService();
    }

    @Test
    @DisplayName("Ensure thread-safe booking prevents duplicate entries")
    void shouldPreventDuplicateBookingsInConcurrentEnvironment() throws InterruptedException {
        final var court = "BANA 1";
        final var time = "10:00";
        final int threadCount = 50;

        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            ;
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        applicationLockService.bookThreadSafe(court, time);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executor.shutdown();
        }

        assertEquals(1, applicationLockService.getActiveBookingsSize());
    }

    @Test
    @DisplayName("Detect race condition when booking concurrently")
    void shouldDetectRaceConditionWhenBookingConcurrently() throws InterruptedException {
        final var court = "BANA 1";
        final var time = "10:00";
        final int threadCount = 50;

        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        applicationLockService.bookNonThreadSafe(court, time);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executor.shutdown();
        }

        assertNotEquals(1, applicationLockService.getActiveBookingsSize());
    }
}