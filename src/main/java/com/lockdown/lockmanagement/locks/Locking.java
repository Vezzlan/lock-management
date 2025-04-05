package com.lockdown.lockmanagement.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Venkat Subramaniam - Functional programming page 117
 * <p>
 * This is bad. We are using a Lock lock field to share the lock
 * between the methods of this class. But the task of locking,
 * for example, within the doOp1() method - leaves a lot to be desired.
 * Its verbose, error-prone and hard to maintain. Use the class Locker
 * instead and harness the power of lambda expressions.
 * **/
@Deprecated
public class Locking {

    Lock lock = new ReentrantLock();

    protected void setLock(final Lock mock) {
        lock = mock;
    }

    public void doOp1() {
        lock.tryLock();
        try {
            //do critical code
            System.out.println("Critical code");
        } finally {
            lock.unlock();
        }
    }
}
