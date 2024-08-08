package com.atguigu.study;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


class MyThread implements Callable<String>
{
    @Override
    public String call() throws Exception
    {
        System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return "hello 0422";
    }
}



/**
 * @auther zzyy
 * @create 2024-08-06 16:47
 */
public class CallableDemo
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        FutureTask<String> futureTask = new FutureTask(new MyThread());
        Thread thread = new Thread(futureTask,"A");
        thread.start();


        FutureTask<String> futureTask2 = new FutureTask(new MyThread());
        Thread thread2 = new Thread(futureTask2,"B");
        thread2.start();

        System.out.println(Thread.currentThread().getName()+"\t"+"task over");


        System.out.println(futureTask.get());
        System.out.println(futureTask2.get());
    }
}
