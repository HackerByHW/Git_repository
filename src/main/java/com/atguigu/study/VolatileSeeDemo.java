package com.atguigu.study;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2024-08-06 21:39
 */
public class VolatileSeeDemo
{
    static Integer flag = 1;

    public static void main(String[] args)
    {
        new Thread(() -> {
            System.out.println("子线程工作内存读取到的flag的值：" + flag);
            while(flag == 1)
            {
                //System.out.println("------haha");
            }
            System.out.println("子线程工作内存读取到的flag的最新的值..." + flag);
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        flag = 2; //主线程修改flag的值
        System.out.println("我是主线程工作内存flag的值：" + flag);
    }
}
