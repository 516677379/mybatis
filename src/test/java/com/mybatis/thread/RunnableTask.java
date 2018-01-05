package com.mybatis.thread;

import lombok.Data;

/**
 * Created by zyf on 2018/1/5.
 */
@Data
public class RunnableTask implements Runnable{

    private Thread t;
    private String threadName;

    RunnableTask(String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    @Override
    public void run() {
        System.out.println("Running " +  threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    public static void main(String[] args){
        new RunnableTask("thread1").start();
        new RunnableTask("thread2").start();
    }
}
