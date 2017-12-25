package com.mybatis.config.redisMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 51667 on 2017/12/22.
 * 这个Receiver类将会被注册为一个消息监听者时。处理消息的方法我们可以任意命名，我们有相当大的灵活性。
 */
@Component
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch;

    /**
     * 注入CountDownLatch，当接收到消息时，调用cutDown()方法
     * @param latch
     */
    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * 定义接收消息的方法
     * @param message
     */
    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        latch.countDown();
    }
}
