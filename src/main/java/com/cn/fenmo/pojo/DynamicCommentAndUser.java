package com.cn.fenmo.pojo;

import java.util.Date;

public class DynamicCommentAndUser {
  private Long mainid;

  private Long dynamicid;

  private Date createdate;

  private String  userName;

  private String content;
  
  private int age;
  
  private int sex;
  
  private String nickName;
  
  private String headImgPath;
  //动态发布者发布动态时离我当前位置的距离
  private int distance;

  private static final long serialVersionUID = 1L;
  
  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getHeadImgPath() {
    return headImgPath;
  }

  public void setHeadImgPath(String headImgPath) {
    this.headImgPath = headImgPath;
  }

  public Long getMainid() {
      return mainid;
  }

  public void setMainid(Long mainid) {
      this.mainid = mainid;
  }

  public Long getDynamicid() {
      return dynamicid;
  }

  public void setDynamicid(Long dynamicid) {
      this.dynamicid = dynamicid;
  }

  public Date getCreatedate() {
      return createdate;
  }

  public void setCreatedate(Date createdate) {
      this.createdate = createdate;
  }
  

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getContent() {
      return content;
  }

  public void setContent(String content) {
      this.content = content;
  }
}
