package com.cn.fenmo.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardingRedisClient {
  private static ShardedJedisPool shardedJedisPool;
  static {
    // ��ȡ��ص�����
    ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
    int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
    int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
    int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));
    String ip = resourceBundle.getString("redis.ip");
    int port = Integer.parseInt(resourceBundle.getString("redis.port"));
    //��������
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(maxActive);
    config.setMaxIdle(maxIdle);
    config.setMaxWaitMillis(maxWait);
    //���÷�ƬԪ����Ϣ
    JedisShardInfo shardInfo1 = new JedisShardInfo(ip,port);
    JedisShardInfo shardInfo2 = new JedisShardInfo(ip,port);
    List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
    list.add(shardInfo1);
    list.add(shardInfo2);
    shardedJedisPool = new ShardedJedisPool(config, list);
  }
  
  /**
   * �򻺴��������ַ�������
   * @param key key
   * @param value value
   */
  public static boolean  set(String key,String value) {
    ShardedJedis jedis = null;
    try {
      jedis = shardedJedisPool.getResource();
      jedis.set(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      shardedJedisPool.returnResource(jedis);
    }
  }
  
  
  /**
   * �򻺴������ö���
   * @param key 
   * @param value
   */
  public static boolean  set(String key,Object value){
    ShardedJedis jedis = null;
    try {
      String objectJson = JSON.toJSONString(value);
      jedis = shardedJedisPool.getResource();
      jedis.set(key, objectJson);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      shardedJedisPool.returnResource(jedis);
    }
  }
  
  
  /**
   * ɾ�������еö��󣬸���key
   * @param key
   */
  public static boolean del(String key){
    ShardedJedis jedis = null;
    try {
      jedis = shardedJedisPool.getResource();
      jedis.del(key);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      shardedJedisPool.returnResource(jedis);
    }
  }
  
  
  /**
   * ����key ��ȡ����
   * @param key
   */
  public static Object get(String key){
    ShardedJedis jedis = null;
    try {
      jedis = shardedJedisPool.getResource();
      Object value = jedis.get(key);
      return value;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      shardedJedisPool.returnResource(jedis);
    }
  }
  
  
  /**
   * ����key ��ȡ����
   * @param key
   */
  public static <T> T get(String key,Class<T> clazz){
    ShardedJedis jedis = null;
    try {
      jedis = shardedJedisPool.getResource();
      String value = jedis.get(key);
      return JSON.parseObject(value, clazz);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }finally{
      shardedJedisPool.returnResource(jedis);
    }
  }
  
}
