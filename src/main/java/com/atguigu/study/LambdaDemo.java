package com.atguigu.study;


interface Foo
{
    //public void sayHello();

    public int add(int x ,int y);
}

/**
 * @auther zzyy
 * @create 2023-10-06 16:46
 *
 *  拷贝小括号，写死右箭头，落地大括号
 */
public class LambdaDemo
{
    public static void main(String[] args)
    {
        /*Foo foo = new Foo()
        {
            @Override
            public void sayHello()
            {
                System.out.println("----hello 0422java");
            }
        };
        foo.sayHello();*/

        /*Foo foo = () -> {
            System.out.println("----hello 0422java v2");
        };
        foo.sayHello();*/

        Foo foo = (x,y) -> {
          System.out.println("---come in add method");
          int result = x + y;
          return result;
        };
        System.out.println(foo.add(2, 5));
    }
}
