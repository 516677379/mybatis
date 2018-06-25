package com.mybatis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by 51667 on 2017/12/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        System.out.println("1");
        for(int i=0;i<10;i++){
            asyncTest(i);
        }
        System.out.println("2");
    }


    @Async
    public void asyncTest(int i){
        try {
            Thread.sleep(1000);
            System.out.println("i:"+new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
