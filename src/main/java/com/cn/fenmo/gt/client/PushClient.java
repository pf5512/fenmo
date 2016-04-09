package com.cn.fenmo.gt.client;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface PushClient {
  public static final int PUSH_BY_CID = 1;
  public static final int PUSH_BY_ALIAS = 2;
  public static final int PUSH_BY_TAGS = 3;

  public final static int PUSH_TYPE_NOTIFICATION = 1;
  public final static int PUSH_TYPE_TRANSMISSION = 2;
  public final static int PUSH_TYPE_TO_ALL = 3;

  public static final String PUSH_FAILURE = "push_failure";
  
  
  public static final String PUSH_FRIEND_ADD    = "friendservice:add:request";
  public static final String PUSH_FRIEND_AGREE  = "friendservice:add:agree";
  public static final String PUSH_FRIEND_REJECT = "friendservice:add:reject";

  /** API 测试 接口 */
  public String interfaceTest(String testStr);

  /**
   * 通知消息
   * 
   * @param title
   *          标题
   * @param message
   *          推送的消息
   * @param pushList
   *          推送的列表
   * @param transmissionMessage
   *          透传消息 可以为null
   * @param listType
   *          推送列表分类 1 cid ，2 alias
   * @return
   */
  public Boolean pushMessageByList(String title, String message,
      String transmissionMessage, List<String> pushList, int listType);

  /**
   * 透传消息,用户不会看到。
   * 
   * @param message
   *          推送的消息
   * @param pushList
   *          推送的列表
   * @param listType
   *          推送列表分类 1 cid 2 alias
   * @return
   */
  public Boolean pushTransmissionMessageByList(String message,
      List<String> pushList, int listType);

  /**
   * 推送所有人
   * 
   * @param title
   *          标题
   * @param stringMessage
   *          消息
   * @param transmissionMessage
   *          透传消息 可以为null
   * @param tagList
   *          推送列表分类
   */
  public Boolean pushMessageToAppByTag(String title, String stringMessage,
      String transmissionMessage, List<String> tagList);

}
