package com.atguigu.study;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.openjdk.jol.info.ClassLayout;

/**
 * @auther zzyy
 * @create 2024-08-06 22:13
 */
public class printObjectHeadDemo
{
    public static void main(String[] args)
    {
// 1、创建对象
        Student student = new Student(5, "tom green");

// 2、打印对象结构分析结果
        System.out.println(ClassLayout.parseInstance(student).toPrintable());
    }
}

@AllArgsConstructor
@NoArgsConstructor
class Student
{

    private Integer stuId;
    private String stuName;
   //private Integer stuAge;
}