package com.mybatis.config.redisMQ;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 51667 on 2017/12/22.
 * Spring Data Redis提供基于Redis发送和接收消息的所有需要的组件，我们只需要配置好三个东西：
 * 一个连接工厂（connection factory）
 *一个消息监听者容器(message listener container)
 *一个Redis的模板(redis template)
 * 我们将通过Redis模板来发送消息，同时将Receiver注册给消息监听者容器。
 * 连接工厂将两者连接起来，使得它们可以通过Redis服务器通信。
 * 如何连接呢？ 我们将连接工厂实例分别注入到监听者容器和Redis模板中即可
 */
@Configuration
public class SubscriberConfig {
    /**
     * 创建连接工厂
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter,new PatternTopic("phone"));//此处可更改topic
        return container;
    }

    /**
     * 绑定消息监听者和接收监听的方法
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }

    /**
     * 注册订阅者
     * @param latch
     * @return
     */
    @Bean
    public Receiver receiver(CountDownLatch latch){
        return new Receiver(latch);
    }

    /**
     * 计数器，用来控制线程
     * @return
     */
    @Bean
    public CountDownLatch latch(){
        return new CountDownLatch(1);//指定了计数的次数 1
    }
}
