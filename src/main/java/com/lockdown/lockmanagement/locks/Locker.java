package com.lockdown.lockmanagement.locks;

import java.util.concurrent.locks.Lock;

public class Locker {

    public static void runLocked(Lock lock, Runnable block) {
        System.out.println("Lock");
        lock.lock();
        try {
            block.run();
        } finally {
            System.out.println("Unlock");
            lock.unlock();
        }
    }
}
