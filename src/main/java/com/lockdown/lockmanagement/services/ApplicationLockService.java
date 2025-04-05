package com.lockdown.lockmanagement.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.lockdown.lockmanagement.locks.Locker.runLocked;

@Service
public class ApplicationLockService {

    private final Lock lock = new ReentrantLock();

    public List<String> activeBookings = new ArrayList<>();

    public void bookThreadSafe(String court, String time) {
        runLocked(lock, () -> trySaveOrder(court, time));
    }

    public void bookNonThreadSafe(String court, String time) {
        trySaveOrder(court, time);
    }

    private void trySaveOrder(String court, String time) {
        final var bookingKey = String.format("%s-%s", court, time);

        if (activeBookings.contains(bookingKey)) {
            System.out.println("Order already saved in memory");
            return;
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        activeBookings.add(bookingKey);
    }

    public int getActiveBookingsSize() {
        return activeBookings.size();
    }
}
