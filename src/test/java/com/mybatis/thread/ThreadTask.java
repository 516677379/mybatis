package com.mybatis.thread;

import org.junit.Test;

/**
 * Created by zyf on 2018/1/5.
 * 1.线程的状态：
 *     新建new 就绪start 运行run 阻塞sleep/suspend/wait/获取synchronize失败/join  死亡
 * 2.优先级
 *   Thread.MIN_PRIORITY[1]--NORM_PRIORITY[5默认]--Thread.MAX_PRIORITY [10]
 * 3.方法
 *   Thread  runnable callable
 *
 *  1.不要用@test test若执行完了，所有线程都会终止
 *  2.常用方法
 *    setName(String name) 线程名称
 *    setPriority(int priority) 线程的优先级
 *    setDaemon(boolean on) 将该线程标记为守护线程或用户线程。
 *    join(long millisec) 等待该线程终止的时间最长为 millis 毫秒
 *    interrupt()
 *    isAlive() 测试线程是否处于活动状态
 *    yield() 暂停当前正在执行的线程对象，并执行其他线程
 *    holdsLock(Object x) 当且仅当当前线程在指定的对象上保持监视器锁时，才返回 true
 *    currentThread() 正在执行的线程对象的引用
 *
 */
public class ThreadTask extends Thread{

    public void run() {
        System.out.println("Running " +  this.getName() );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + Thread.currentThread().getName() + ", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  Thread.currentThread().getName() + " interrupted.");
        }
        System.out.println("Thread " +  Thread.currentThread().getName() + " exiting.");
    }

    @Test
    public void test(){
        new ThreadTask().start();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args){
        new ThreadTask().start();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
