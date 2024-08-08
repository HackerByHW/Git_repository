package com.atguigu.study;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2024-08-06 21:55
 */
public class LockUpDemo
{


    public static void main(String[] args)
    {

        // 1、创建一个对象作为锁对象
        Object lock = new Object();

// 2、没有线程申请锁
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

// 3、main 线程申请锁
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

// 4、创建两个线程申请锁
        new Thread(() -> {
            synchronized (lock) {
                while (true) {
                }
            }
        }, "thread-a").start();

        new Thread(() -> {
            synchronized (lock) {
                while (true) {
                }
            }
        }, "thread-b").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }
}
