package org.oza.ego.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Import(PropertiesConfiguration.class)
public class JedisConfiguration {

    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(30);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setNumTestsPerEvictionRun(1024);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(180000);
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(10000);
        jedisPoolConfig.setMaxWaitMillis(1500);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setBlockWhenExhausted(false);
        return jedisPoolConfig;
    }

    @Bean
    public JedisCluster getJedisCluster(JedisPoolConfig jedisPoolConfig,
                                        @Value("${jedis.node1.host}") String host1, @Value("${jedis.node1.port}") int port1,
                                        @Value("${jedis.node2.host}") String host2, @Value("${jedis.node2.port}") int port2,
                                        @Value("${jedis.node3.host}") String host3, @Value("${jedis.node3.port}") int port3,
                                        @Value("${jedis.node4.host}") String host4, @Value("${jedis.node4.port}") int port4,
                                        @Value("${jedis.node5.host}") String host5, @Value("${jedis.node5.port}") int port5,
                                        @Value("${jedis.node6.host}") String host6, @Value("${jedis.node6.port}") int port6) {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort(host1, port1));
        nodes.add(new HostAndPort(host2, port2));
        nodes.add(new HostAndPort(host3, port3));
        nodes.add(new HostAndPort(host4, port4));
        nodes.add(new HostAndPort(host5, port5));
        nodes.add(new HostAndPort(host6, port6));
        return new JedisCluster(nodes, jedisPoolConfig);
    }
}
