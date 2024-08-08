package com.atguigu.study;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @auther zzyy
 * @create 2024-08-02 21:14
 */
public class ReadWriteLockDemo2
{
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void testOK(){
        rwLock.writeLock().lock();
        System.out.println("获取到写锁====开写");

        rwLock.readLock().lock();
        System.out.println("获取到读锁----------开读");
        rwLock.writeLock().unlock();

        //读数据
        //可以插入其他的读线程（因为读读可并发），从而提高应用程序的执行效率

        System.out.println("释放写锁==============");
        rwLock.readLock().unlock();
        System.out.println("释放读锁++++++++++++++++");
    }














    //错误的锁降级示例，因为在释放写锁和获取读锁之间能够有其他的写线程插入，从而影响数据的一致性
    public void testError(){
        rwLock.writeLock().lock();
        System.out.println("获取到写锁。。。。");

        System.out.println("释放写锁==============");
        rwLock.writeLock().unlock();

        //可能插入其他写线程

        System.out.println("获取到读锁----------");
        rwLock.readLock().lock();

        rwLock.readLock().unlock();
        System.out.println("释放读锁++++++++++++++++");
    }
    
    public static void main(String[] args)
    {
        new ReadWriteLockDemo2().testError();
    }
}
