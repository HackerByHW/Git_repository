package com.atguigu.study;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @auther zzyy
 * @create 2024-08-02 21:29
 * 写线程首先获取写锁，更新数据，然后将锁降级为读锁。读线程在获取读锁后读取数据。
 * 这个过程演示了锁降级的概念，允许多个线程在持有读锁的情况下同时访问数据，
 * 提高了并发性能。`
 * 请注意，在锁降级期间，我们要确保数据的一致性，即在降级为读锁之前和之后都可以正确读取数据。
 * 这是锁降级的关键部分。（先获取读锁，再释放写锁）`
 */
public class LockDowngradingDemo {

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    private int data = 0;

    public int readData() {
        readLock.lock();
        try {
            return data;
        } finally {
            readLock.unlock();
        }
    }

    public void updateData(int newData) {
        writeLock.lock();
        try {
            data = newData; // 更新数据
            System.out.println("Data updated to: " + newData);

            // 锁降级，（先获取读锁，再释放写锁）,读己之所写
            readLock.lock(); // 获取读锁
            System.out.println("Lock downgraded to read lock.");
        } finally {
            writeLock.unlock(); // 释放写锁
        }

        try {
            // 在持有读锁的情况下执行其他操作，读己之所写
            int currentData = readData();
            System.out.println("Read currentData: " + currentData);
        } finally {
            readLock.unlock(); // 释放读锁
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        LockDowngradingDemo example = new LockDowngradingDemo();

        new Thread(() -> {
            example.updateData(422);
        }).start();

        new Thread(() -> {
            int currentData = example.readData();
            System.out.println("Reader got data: " + currentData);
        }).start();
    }
}
