package com.atguigu.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @auther zzyy
 * @create 2024-08-07 11:39
 */
public class MyThreadPoolDemo
{
    public static void main(String[] args)
    {

        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors()*2,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try
        {
            //模拟N个顾客进入银行办理业务（请求线程），
            for (int i = 1; i <= 8; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t"+"给顾客："+ finalI +"\t办理业务");
                    try { TimeUnit.MILLISECONDS.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

    }

    private static void initPool()
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5工作线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池一线程
        //ExecutorService threadPool = Executors.newCachedThreadPool();//一池N线程

        try
        {
            //模拟20个顾客进入银行办理业务（请求线程），
            for (int i = 1; i <= 20; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t"+"给顾客："+ finalI +"\t办理业务");
                    //暂停几秒钟线程
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}


class ShareMyData
{
    int number = 5;
}