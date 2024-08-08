package com.atguigu.study;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2024-08-07 10:50
 */
public class DeadLockDemo
{
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args)
    {
        new Thread(() -> {
            synchronized (lockA){
                System.out.println(Thread.currentThread().getName()+"\t"+"---自己持有A锁，试图获得B锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockB){
                    System.out.println(Thread.currentThread().getName()+"\t"+"---获得B锁成功");
                }
            }
        },"A").start();


        new Thread(() -> {
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t"+"---自己持有B锁，试图获得A锁");
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockA){
                    System.out.println(Thread.currentThread().getName()+"\t"+"---获得A锁成功");
                }
            }
        },"B").start();
    }
}
