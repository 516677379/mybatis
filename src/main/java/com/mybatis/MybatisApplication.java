package com.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching//开启缓存
@EnableScheduling//开启定时任务
@MapperScan("com.mybatis.dao")
public class MybatisApplication {
	public static void main(String[] args) {
		ApplicationContext ctx =SpringApplication.run(MybatisApplication.class, args);

		//try {
		//	StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		//	CountDownLatch latch = ctx.getBean(CountDownLatch.class);
        //
		//	System.out.println("Sending message...");
		//	template.convertAndSend("chat", "Hello from Redis!");
        //
		//	latch.await();
        //
		//	System.exit(0);
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}
	}
}
