package com.cn.fenmo.pojo;

import java.io.Serializable;

public class RoomBjImg implements Serializable{
  private static final long serialVersionUID = 1L;
  private long id;
  private String userName;
  private String groupId;
  private String bjImgUrl;
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getGroupId() {
    return groupId;
  }
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
  public String getBjImgUrl() {
    return bjImgUrl;
  }
  public void setBjImgUrl(String bjImgUrl) {
    this.bjImgUrl = bjImgUrl;
  }
}
