//package com.e3mall.redis;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.e3mall.common.jedis.JedisClient;
//
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//
//public class JedisTest {
//	
//	
//	
//	public static void main(String[] args) {
//		JedisTest jedisTest = new JedisTest();
//		try {
//			jedisTest.Jedis2();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	
//	//单机版
//	@Test
//	public void Jedis1(){
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
////		JedisPool jedisPool = new JedisPool("101.132.183.157", 6379);
//		JedisPool jedisPool = (JedisPool)context.getBean(JedisPool.class);
//		Jedis jedis = jedisPool.getResource();
//		jedis.set("a", "b");
//		
//		System.out.println(jedis.get("a"));
//		jedis.close();
//		jedisPool.close();
//	
//	}
//	
//	@Test
//	//集群版本测试
//	public void Jedis2() throws Exception{
//	/*	Set<HostAndPort> nodes = new HashSet<HostAndPort>();
//		nodes.add(new HostAndPort("101.132.183.157", 7001));
//		nodes.add(new HostAndPort("101.132.183.157", 7002));
//		nodes.add(new HostAndPort("101.132.183.157", 7003));
//		nodes.add(new HostAndPort("101.132.183.157", 7004));
//		nodes.add(new HostAndPort("101.132.183.157", 7005));
//		nodes.add(new HostAndPort("101.132.183.157", 7006));
//		JedisCluster jedisCluster = new JedisCluster(nodes);*/
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
//		JedisClient JedisClient = (JedisClient) context.getBean(JedisClient.class);
//		System.out.println(JedisClient.get("cluster"));
//		
//	}
//}
