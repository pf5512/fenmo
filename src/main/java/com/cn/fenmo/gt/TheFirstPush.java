package com.cn.fenmo.gt;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
public class TheFirstPush {
    //首先定义一些常量, 修改成开发者平台获得的值
    private static String appId = "aKGOV6bVQtA4OMEkwDV459";
    private static String appKey = "a8Rg9H9e1Q7fPuIiZS0uT8";
    private static String masterSecret = "oEu0PwMBTu8nyeNexIoZa2";
    private static String url = "http://sdk.open.api.igexin.com/serviceex";
    
    public static void  pushNotie() throws IOException{
      // 新建一个IGtPush实例，传入调用接口网址，appkey和masterSecret
      IGtPush push = new IGtPush(url, appKey, masterSecret);
      push.connect();
      // 新建一个消息类型，根据app推送的话要使用AppMessage
      AppMessage message = new AppMessage();
      // 新建一个推送模版, 以链接模板为例子，就是说在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页
      LinkTemplate template = new LinkTemplate();
      template.setAppId(appId);
      template.setAppkey(appKey);
      template.setIsRing(true);
      template.setTitle("欢迎使用个推!");
      template.setText("这是一条推送消息~");
      template.setUrl("http://baidu.com");
      List<String> appIds = new ArrayList<String>();
      appIds.add(appId);
      // 模板设置好后塞进message里并设置，同时设置推送的app id,还可以配置这条message是否支持离线，以及过期时间等
      message.setData(template);
      message.setAppIdList(appIds);
      message.setOffline(true);
      message.setOfflineExpireTime(1000 * 600);
      // 调用IGtPush实例的pushMessageToApp接口，参数就是上面我们配置的message
      IPushResult ret = push.pushMessageToApp(message);
      System.out.println(ret.getResponse().toString());
   } 
   public static void  pushNotie2() throws IOException{
      // 新建一个IGtPush实例，传入调用接口网址，appkey和masterSecret
      IGtPush push = new IGtPush(url, appKey, masterSecret);
      push.connect();
      // 新建一个消息类型，根据app推送的话要使用AppMessage
      AppMessage message = new AppMessage();
      // 新建一个推送模版, 以链接模板为例子，就是说在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页
      List<String> appIds = new ArrayList<String>();
      appIds.add(appId);
      // 模板设置好后塞进message里并设置，同时设置推送的app id,还可以配置这条message是否支持离线，以及过期时间等
      message.setData(getLinkTemplate("标题","内容","https://baidu.com"));
      message.setAppIdList(appIds);
      message.setOffline(true);
      message.setOfflineExpireTime(1000 * 600);
      // 调用IGtPush实例的pushMessageToApp接口，参数就是上面我们配置的message
      IPushResult ret = push.pushMessageToApp(message);
   }
   public static LinkTemplate getLinkTemplate(String title,String conetnt,String url) {
     LinkTemplate template = new LinkTemplate();
     template.setAppId(appId);
     template.setAppkey(appKey);
     template.setTitle(title);
     template.setText(conetnt); 
     template.setUrl(url);
     return template;
  }
   
   
   

   public static TransmissionTemplate transmissionTemplateDemo(String conetnt) {
     TransmissionTemplate template = new TransmissionTemplate();
     template.setAppId(appId);
     template.setAppkey(appKey);
     // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
     template.setTransmissionType(2);
     template.setTransmissionContent(conetnt);
      // 设置定时展示时间
      // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
     return template;
  }
   
   public static void main(String[] args) throws IOException {
       pushNotie();
     
   }
}