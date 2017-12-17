//package com.mybatis.cache;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created by 51667 on 2017/12/17.
// */
//@Configuration
//public class RedisConfiguration {
//    @Value("${spring.redis.cluster.nodes}")
//    private String clusterNodes;
//
//    @Bean
//    public JedisCluster getJedisCluster() {
//        //分割集群节点
//        String[] sNodes = clusterNodes.split(",");
//        Set<HostAndPort> nodes = new HashSet<>();
//        for (String node : sNodes) {
//            String[] hp = node.split(":");
//            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
//        }
//        //创建redis集群对象
//        JedisCluster jedisCluster = new JedisCluster(nodes);
//        return jedisCluster;
//    }
//}
