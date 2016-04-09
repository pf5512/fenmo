package com.cn.fenmo.redis;

import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.cn.fenmo.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
  //池化管理jedis链接池
  public  static  JedisPool jedisPool; 
  static {
    //读取相关的配置
    ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
    int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
    int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
    int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));
    String ip = resourceBundle.getString("redis.ip");
    int port = Integer.parseInt(resourceBundle.getString("redis.port"));
    JedisPoolConfig config = new JedisPoolConfig();  
    config.setMaxTotal(maxActive);
    config.setMaxIdle(maxIdle);
    config.setMaxWaitMillis(maxWait);
    jedisPool = new JedisPool(config, ip, port,100000); 
  }
  
  public static boolean TestRedisIsSuccess(){
    return jedisPool==null?false:true;
  }
  
  /**
   * 向缓存中设置字符串内容
   * @param key key
   * @param value value
   */
  public static boolean  set(String key,String value){
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      jedis.set(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }
  
  /**
   * 向缓存中设置字符串内容
   * @param key key
   * @param value value
   * @param time time过期时间，单位秒
   */
  public static boolean  set(String key,String value,int time){
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      jedis.set(key, value);
      jedis.expire(key, time);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }
  
  /**
   * 向缓存中设置对象
   * @param key 
   * @param value
   */
  public static boolean  set(String key,Object value){
    Jedis jedis = null;
    try {
      String objectJson = JSON.toJSONString(value);
      jedis = jedisPool.getResource();
      jedis.set(key, objectJson);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }
  
  public static boolean setObject(String key,Object value){
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      byte[]  obj =  SerializeUtil.serialize(value);
      jedis.set(key.getBytes(),obj);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }
  
  /**
   * 向缓存中设置对象
   * @param key 
   * @param value
   * @param time time过期时间，单位秒
   */
  public static boolean setObject(String key,Object value,int time){
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      byte[]  obj =  SerializeUtil.serialize(value);
      jedis.set(key.getBytes(),obj);
      jedis.expire(key,time);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }
  
  
  /**
   * 删除缓存中得对象，根据key
   * @param key
   */
  public static boolean del(String key){
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      jedis.del(key);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }
 
  /**
   * 根据key 获取内容
   * @param key
   */
  public static Object get(String key){
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      Object value = jedis.get(key);
      return value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }
  
  public static Object getObject(String key){
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      byte[] value = jedis.get(key.getBytes());
      return SerializeUtil.unserialize(value);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }finally{
      jedisPool.returnResource(jedis);
    }
  }

}
