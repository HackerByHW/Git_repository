package com.atguigu.study;


import javax.print.attribute.standard.RequestingUserName;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket //资源类，车票  OOP 对象 = Field + Method
{
    int number = 50;//初始50张票 ，Filed

    //高内聚，资源类自身携带对外提供服务的业务方法，对外暴露，这个就是业务方法Method

    /*public synchronized void saleTicket() //Method
    {
        if(number > 0)
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"卖出第: "+(number--)+"\t 还剩下: "+number);
        }
    }*/

    /*public void saleTicket() //Method
    {
        synchronized (Object.class) {
            if(number > 0)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"卖出第: "+(number--)+"\t 还剩下: "+number);
            }
        }
    }*/

    //今天的8.5的新知识
    //ReentrantLock 默认是非公平锁+独占锁，AQS
    private final Lock lock = new ReentrantLock(true);//ReentrantLock()独占锁，synchronized也是独占锁
    public void saleTicket() //Method
    {
        lock.lock();
        try
        {
            if(number > 0)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"卖出第: "+(number--)+"\t 还剩下: "+number);
            }
        }finally {
            lock.unlock();
        }
    }
}



/**
 * @auther zzyy
 * @create 2024-08-02 20:45
 *
 *三个售票员    卖出      50张票
 *如何编写企业级需要的工程化代码？ 多对1 多个线程操作同一个资源类
 *
 * JUC小口诀
 *
 * 1 高聚低耦，      线程    操作  资源类
 *
 */
public class SaleTicketDemo
{
    public static void main(String[] args) //入口程序
    {
        Ticket ticket = new Ticket();

        new Thread(() -> {for (int i = 0; i < 51; i++) ticket.saleTicket();}, "A").start();
        new Thread(() -> {for (int i = 0; i < 51; i++) ticket.saleTicket();}, "B").start();
        new Thread(() -> {for (int i = 0; i < 51; i++) ticket.saleTicket();}, "C").start();


        //Thread(Runnable target, String name) Allocates a new Thread object.

        /*new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < 51; i++) {
                    ticket.saleTicket();
                }
            }
        }, "A").start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < 51; i++) {
                    ticket.saleTicket();
                }
            }
        }, "B").start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < 51; i++) {
                    ticket.saleTicket();
                }
            }
        }, "C").start();*/



    }
}
