package com.atguigu.study;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


class MyCache
{
    volatile Map<String,String> map = new HashMap<>();

    //V1
    /*
    private Lock lock = new ReentrantLock();
    public void write(String key,String value)
    {
        lock.lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---开始写入");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t"+"---结束写入");
        }finally {
            lock.unlock();
        }
    }

    public void read(String key)
    {
        lock.lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---开始读取");
            String retValue = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t"+"---结束读取："+retValue);
        }finally {
            lock.unlock();
        }
    }
    */

    //V2 读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key,String value)
    {
        rwLock.writeLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---开始写入");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t"+"---结束写入");
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key)
    {
        rwLock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---开始读取");
            String retValue = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t"+"---结束读取："+retValue);
        }finally {
            rwLock.readLock().unlock();
        }
    }
}



/**
 * @auther zzyy
 * @create 2024-08-2 8:20
 * 读写锁
 *
 * 对于同一个资源，我们涉及多线程的操作，有读，有写，交替进行。
 * 为了保证读写的数据一致性。
 *
 * 读 读 可共享
 * 读 写 不共享
 * 写 写 不共享
 * 读的时候希望高并发同时进行，可以共享，可以多个线程同时操作进行中.....
 * 写的时候为了保证数据一致性，需要独占排它。
 *
 *
 * 题目：5个线程读，5个线程写入，操作同一个资源
 *
 *
 * 1 不加锁                不可以，写的时候原子性被破坏
 * 2 加ReentrantLock锁     写控制了，但是没有并发读，并发性能不好
 * 3 加读写锁               规范写入，写唯一，读并发
 */
public class ReadWriteLockDemo
{
    // 题目：5个线程读，5个线程写入，操作同一个资源

    public static void main(String[] args)
    {
        MyCache myCache = new MyCache();

        for (int i = 1; i <=5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.write(finalI +"", finalI +"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <=5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.read(finalI +"");
            },String.valueOf(i)).start();
        }
    }
}

