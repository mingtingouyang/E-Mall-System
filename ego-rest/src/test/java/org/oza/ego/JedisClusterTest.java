package org.oza.ego;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.JedisCluster;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-jedis.xml", "classpath:spring-mvc.xml", "classpath:spring-mybatis.xml"})
public class JedisClusterTest {

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void connectionTest() {
        String result = jedisCluster.get("test");
        System.out.println(result);
    }
}
