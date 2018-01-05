package com.mybatis.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by zyf on 2018/1/5.
 * 与runnable主要区别有返回值
 * 创建 Callable 实现类的实例，使用 FutureTask 类来包装 Callable 对象，该 FutureTask 对象封装了该 Callable 对象的 call() 方法的返回值
 * 使用 FutureTask 对象作为 Thread 对象的 target 创建并启动新线程
 * 调用 FutureTask 对象的 get() 方法来获得子线程执行结束后的返回值
 */
public class CallableTask implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int i=0;
        for(;i<10;i++){
            System.out.println("i:"+i+"  "+Thread.currentThread().getName());
        }
        return i;
    }

    public static void main(String[] args){
        try {
            CallableTask  task=new CallableTask();
            FutureTask<Integer> futureTask=new FutureTask<Integer>(task);
            for(int i = 0;i < 30;i++)
            {
                System.out.println(Thread.currentThread().getName()+" 的循环变量i的值"+i);
                if(i==20)
                {
                    new Thread(futureTask,"有返回值的线程").start();
                }
            }
            System.out.println("子线程的返回值："+futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
