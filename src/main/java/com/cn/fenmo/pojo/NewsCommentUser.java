package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

public class NewsCommentUser implements Serializable {
    private Long mainid;

    private String content;

    private Date createdate;

    private String userName;
    
    private int age;
    
    private int sex;
    
    private String nickName;
    
    private String headImgPath;

    private static final long serialVersionUID = 1L;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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



}