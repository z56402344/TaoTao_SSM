package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

public class JedisTest {

    //单独
    @Test
    public void testJedisTestSingle(){
        Jedis jedis = new Jedis("192.168.2.129",7002);
        jedis.set("key1","key1 test");
        String str = jedis.get("key1");
        System.out.println(str);
        //关闭jedis
        jedis.close();

    }


    @Test
    public void testJedisPool(){
        JedisPool pool = new JedisPool("192.168.2.129",7002);
        Jedis jedis = pool.getResource();
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //让连接池回收
        jedis.close();
    }

    //redis集群版
    @Test
    public void testJedisCluster(){
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.2.129",7001));
        nodes.add(new HostAndPort("192.168.2.129",7002));
        nodes.add(new HostAndPort("192.168.2.129",7003));
        nodes.add(new HostAndPort("192.168.2.129",7004));
        nodes.add(new HostAndPort("192.168.2.129",7005));
        nodes.add(new HostAndPort("192.168.2.129",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("key2","1000");
        String key2 = jedisCluster.get("key2");
        System.out.println(key2);

    }

    @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        jedis.close();
        pool.close();
    }

    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
        String string = jedisCluster.get("key1");
        System.out.println(string);
        jedisCluster.close();
    }


}
