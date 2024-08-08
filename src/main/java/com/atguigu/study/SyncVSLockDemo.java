package com.atguigu.study;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther zzyy
 * @create 2024-08-02 20:36
 */
public class SyncVSLockDemo
{
    public synchronized void m1()
    {
        System.out.println(Thread.currentThread().getName()+"\t"+"----come in");
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private Lock lock = new ReentrantLock();

    public void m2()
    {
        if (lock.tryLock())
        {
          try
          {
              System.out.println(Thread.currentThread().getName()+"\t"+"----come in");
              try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
          } finally {
            lock.unlock();
          }
        } else {
            System.out.println(Thread.currentThread().getName()+"\t"+"抢锁失败，下次再来");
        }
    }


    public void m3()
    {
        try
        {
            if (lock.tryLock(4,TimeUnit.SECONDS))
            {
                try
                {
                    System.out.println(Thread.currentThread().getName()+"\t"+"----come in");
                    try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName()+"\t"+"抢锁失败已经等待2秒，下次再来");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args)
    {
        SyncVSLockDemo resource = new SyncVSLockDemo();

        new Thread(() -> {
            resource.m3();
        },"t1").start();

        new Thread(() -> {
            resource.m3();
        },"t2").start();


    }
}
