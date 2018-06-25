package com.mybatis;

import org.junit.Test;

/**
 * Created by zyf on 2018/1/5.
 */

public class AsyncTest {

    @Test
    public void doTask() throws InterruptedException{
        AsyncTask asyncTask=new AsyncTask();

        long currentTimeMillis = System.currentTimeMillis();
        asyncTask.task1();
        asyncTask.task2();
        asyncTask.task3();
        long currentTimeMillis1 = System.currentTimeMillis();
        String str= "task任务总耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms";
        System.out.println("str:"+str);
    }

    @Test
    public void doTask2() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
          for(int i=0;i<3;i++){
             new Thread(()->{
                 try {
                     System.out.println(Thread.currentThread().getName());
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }).start();}
        long currentTimeMillis1 = System.currentTimeMillis();
        String str= "task任务总耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms";
        System.out.println("str:"+str);
    }
}
