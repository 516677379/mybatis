package com.mybatis.thread;

/**
 * Created by zyf on 2018/1/5.
 * 同步
 *    1.synchronized; public [static] sysnchronized void method(){..}
 *      synchronized同步并不高级，要同一个对象实例才有用
 *
 */
public class ThreadSynchronized {

    private int count;

    /**
     * sysnchronized 只有一个线程在同步实例方法中执行
     * @param value
     */
    public synchronized void add(int value){
        count+=value;
    }

    /**
     * 1同步方法块
     * 注：synchronized(this){  ..  } 中的this 为调用add方法的实例本身
     *  在同步构造器中用括号括起来的对象叫做监视器对象。上述代码使用监视器对象同步，同步实例方法使用调用方法本身的实例作为监视器对象
     *  在一个监视器对象上一次只能有一个线程在同步块内执行
     * @param value
     */
    public void addArea(int value){
        synchronized (this){//this可xx.class
            this.count+=value;
        }
    }

    protected boolean someSign=false;

    /**
     *  2忙等待
     */
    public void busyWait(){
        while(!false){
            System.out.println("忙等待直到someSign被某个线程更改 否则一直循环下去 占cpu");
        }
    }


    /**
     *   wait/notify/notifyAll();
     *  1.为了调用wait()或者notify()，线程必须先获得那个对象的锁。也就是说，线程必须在同步块里调用wait()或者notify()
     *  2.当一个线程调用一个对象的notify()方法，正在等待该对象的所有线程中将有一个线程被唤醒并允许执行
     *  （校注：这个将被唤醒的线程是随机的，不可以指定唤醒哪个线程）。同时也提供了一个notifyAll()方法来唤醒正在等待一个给定对象的所有线程
     *  3.一旦线程调用了wait()方法，它就释放了所持有的监视器对象上的锁。这将允许其他线程也可以调用wait()或者notify()
     */
    Object obj=new Object();
    public void doWait() throws InterruptedException{
        synchronized (obj.getClass()){
            obj.wait();
        }
    }
    public void doNotify() throws InterruptedException{
        synchronized (obj.getClass()){
            obj.notify();
        }
    }

    public void waitNotify(){
        //先调用dowait();  满足条件调用donotify();
    }


    /**
     * 4.丢失的信号[先调用了notify 后调用wait()]大概同上，加了一个判断
     *
     */
    Object obj2=new Object();
    boolean isSigned=false;
    public void doWait2() throws InterruptedException{
        synchronized (obj.getClass()){
            if(!isSigned){
                obj.wait();
            }
            isSigned=false;
        }
    }
    public void doNotify2() throws InterruptedException{
        synchronized (obj.getClass()){
            isSigned = true;
            obj.notify();
        }
    }


    /**
     * 5假唤醒：由于莫名其妙的原因，线程有可能在没有调用过notify()和notifyAll()的情况下醒来
     * 为了防止假唤醒，保存信号的成员变量将在一个while循环里接受检查，而不是在if表达式里。这样的一个while循环叫做自旋锁
     * 占cpu慎重
     * @throws InterruptedException
     */
    public void doWait3() throws InterruptedException{
        synchronized (obj.getClass()){
            while(!isSigned){
                obj.wait();
            }
            isSigned=false;
        }
    }

}
