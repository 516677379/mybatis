package com.mybatis.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * Created by zyf on 2018/1/9.
 * 线程执行任务类
 */
@Service
public class AsyncTaskService {
    Random random=new Random();

    @Async
    public void executeAsyncTask(int i){
        System.out.println("做异步执行处理，然后执行任务 "+i);
    }

    /**
     * 异常调用返回future
     * future.get()获取结果
     * @param i
     * @return
     */
    @Async
    public Future<String> asyncInvokeReturnFuture(int i)throws InterruptedException{
        System.out.println("input is "+ i);
        Thread.sleep(1000 * random.nextInt(i));
        // Future接收返回值，这里是String类,可根据实际情况自定义
        Future<String> future = new AsyncResult<String>("success:" + i);
        return future;
    }
}
