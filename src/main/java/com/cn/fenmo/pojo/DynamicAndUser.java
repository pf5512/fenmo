package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

public class DynamicAndUser implements Serializable {
    private Long mainid;

    private String content;

    private String userName;
    
    private String imgUrl;

    private Date createdate;

    private String imgpath;
    
    private int zcount;
    
    private int age;
    
    private int sex;
    
    private int comments;
    
    private String nickName;
    
    private String headImgPath;
    
    private double lat;
    
    private double lng;
    
    private double distance;

    private static final long serialVersionUID = 1L;
    
    public int getComments() {
      return comments;
    }

    public void setComments(int comments) {
      this.comments = comments;
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

    public int getZcount() {
      return zcount;
    }

    public void setZcount(int zcount) {
      this.zcount = zcount;
    }

    public Long getMainid() {
        return mainid;
    }

    public void setMainid(Long mainid) {
        this.mainid = mainid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getImgpath() {
      return imgpath;
    }

    public void setImgpath(String imgpath) {
      this.imgpath = imgpath;
    }

    public double getLat() {
      return lat;
    }

    public void setLat(double lat) {
      this.lat = lat;
    }

    public double getLng() {
      return lng;
    }

    public void setLng(double lng) {
      this.lng = lng;
    }

    public double getDistance() {
      return distance;
    }

    public void setDistance(double distance) {
      this.distance = distance;
    }

    public String getImgUrl() {
      return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
    }
}