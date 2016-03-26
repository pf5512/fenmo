package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

import com.cn.fenmo.util.DateUtil;

public class NewsComment implements Serializable {
    private Long mainid;

    private String content;

    private Long newsid;

    private String createdate;

    private String userName;
    
    private String nickName;
    
    private String headImgPath;
    
    private int sex;
    
    private int age;
    
    private int zcount;

    public int getZcount() {
      return zcount;
    }

    public void setZcount(int zcount) {
      this.zcount = zcount!=0?zcount:0;
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

    public int getSex() {
      return sex;
    }

    public void setSex(int sex) {
      this.sex = sex;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    private static final long serialVersionUID = 1L;

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

    public Long getNewsid() {
        return newsid;
    }

    public void setNewsid(Long newsid) {
        this.newsid = newsid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
      this.createdate =  DateUtil.getDateSecondDashFormat(createdate);
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }



}