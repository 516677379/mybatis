package com.mybatis.thread;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zyf on 2018/1/9.
 */
public class ThreadSyncTest {

    private int count=0;

    private Lock lock=new ReentrantLock();

    /**
     * 1.sysnchronized关键字
     * @throws InterruptedException
     */
    public synchronized void syncTest()throws InterruptedException{
        System.out.println("当前线程"+Thread.currentThread().getName());
        Thread.sleep(1000);
    }

    /**
     * 该代码块可以保证在同一时间只有一个线程可以执行++count
     */
    public void syncInnerTest(){
            synchronized (this){
                ++count;
            }
    }

    /**
     * 效果同上
     * 该代码块可以保证在同一时间只有一个线程可以执行return ++count
     */
    public void lockFirst(){
        lock.lock();
        try {
            ++count;
        } finally {
            lock.unlock();//必须放在fianlly保证锁释放
        }
    }

    /**
     * 利用Semaphore并发包
     * Semaphore是基于计数的信号量，它可以设定一个资源的总数量，基于这个总数量，多线程竞争获取许可信号，
     * 做自己的申请后归还，超过总数量后，线程申请许可，信号将会被阻塞。等到有资源时，继续执行。
     *
     * 单线程 效率不高
     */
    Semaphore semaphore=new Semaphore(2);//定义资源总量

    public String semaphoreTest(){
        int availablePermits=semaphore.availablePermits();//可用资源数
        if(availablePermits>0){
            System.out.println("抢到资源");
        }else{
            System.out.println("资源已被占用，稍后再试");
            return "Resource is busy！";
        }
        try {
            semaphore.acquire(1);  //请求占用一个资源
            System.out.println("资源正在被使用");
            Thread.sleep(3000);//放大资源占用时间，便于观察
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            semaphore.release(1);//释放一个资源
        }
        return "Success";
    }

    @Test
    public void test(){
        String str=new ThreadSyncTest().semaphoreTest();
        System.out.println(str);
        String str2=new ThreadSyncTest().semaphoreTest();
        System.out.println(str2);
    }

    class Data{
        private ReadWriteLock rwl = new ReentrantReadWriteLock();
        private  int data=0;
        public void set(int data) {
            rwl.writeLock().lock();// 取到写锁
            try {
                System.out.println(Thread.currentThread().getName() + "准备写入数据");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.data = data;
                System.out.println(Thread.currentThread().getName() + "写入" + this.data);
            } finally {
                rwl.writeLock().unlock();// 释放写锁
            }
        }
        public void get() {
            rwl.readLock().lock();// 取到读锁
            try {
                System.out.println(Thread.currentThread().getName() + "准备读取数据");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "读取" + this.data);
            } finally {
                rwl.readLock().unlock();// 释放读锁
            }
        }

    }
    public Data getData(){
        return new Data();
    }
    public static void main(String[] args){
        final Data data=new ThreadSyncTest().getData();
        for(int i=0;i<3;i++){
            new Thread(()->{
                 data.set(new Random().nextInt());
            }).start();
        }
        for(int j=0;j<3;j++){
            new Thread(()->{
                 data.get();
            }).start();
        }
    }
}
