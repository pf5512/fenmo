package com.cn.fenmo.gt.push;

import java.util.List;

public class PushObject {

  private String title;// 标题
  private String message;// 信息
  private String transmissionMessage;// 透传消息内容
  private List<String> pushList;// 推送的列表
  private int listType;
  private int pushType;

  public PushObject() {

  }

  public PushObject(String title, String message, String transmissionMessage,
      List<String> pushList, int listType, int pushType) {
    this.title = title;
    this.message = message;
    this.transmissionMessage = transmissionMessage;
    this.pushList = pushList;
    this.listType = listType;
    this.pushType = pushType;
  }
  

  public PushObject(String title, String message, String transmissionMessage,
      List<String> pushList,  int pushType) {
    this.title = title;
    this.message = message;
    this.transmissionMessage = transmissionMessage;
    this.pushList = pushList;
    this.pushType = pushType;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTransmissionMessage() {
    return transmissionMessage;
  }

  public void setTransmissionMessage(String transmissionMessage) {
    this.transmissionMessage = transmissionMessage;
  }

  public List<String> getPushList() {
    return pushList;
  }

  public void setPushList(List<String> pushList) {
    this.pushList = pushList;
  }

  public int getListType() {
    return listType;
  }

  public void setListType(int listType) {
    this.listType = listType;
  }

  public int getPushType() {
    return pushType;
  }

  public void setPushType(int pushType) {
    this.pushType = pushType;
  }
  
  @Override
  public String toString(){
    
    return this.message;
  }
}
