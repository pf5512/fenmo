package com.cn.fenmo.redis;

import com.cn.fenmo.pojo.UserBean;

public class Test {
  
  public static void userCache(){
    //向缓存中保存对象
    UserBean zhuoxuan = new UserBean();
    zhuoxuan.setSex(1);
    zhuoxuan.setUsername("硝酸钠");
    //调用方法处理
    boolean reusltCache = RedisClient.setObject("zhuoxuan", zhuoxuan,1);
    if (reusltCache) {
      System.out.println("向缓存中保存对象成功。");
    }else{
      System.out.println("向缓存中保存对象失败。");
    }
  }
  
  public static void main(String args[]) throws InterruptedException {
    // userCache();
    // RedisClient.set("6", "18");
    // UserBean zhuoxuan =  (UserBean) RedisClient.getObject("zhuoxuan");
    // System.out.println(RedisClient.get("6"));
    // System.out.println(zhuoxuan.getSex());
     RedisClient.get("15867178340");
  }
}
