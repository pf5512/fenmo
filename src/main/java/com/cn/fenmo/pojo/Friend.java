package com.cn.fenmo.pojo;

import java.io.Serializable;

public class Friend implements Serializable {
    private Long mainid;

    private String userphone;

    private String friendphone;

    private Integer state;
    
    private String nickName;
    
    private String friendNickName;

    private static final long serialVersionUID = 1L;

    
    public String getFriendNickName() {
      return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
      this.friendNickName = friendNickName;
    }

    public String getNickName() {
      return nickName;
    }

    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    public Long getMainid() {
        return mainid;
    }

    public void setMainid(Long mainid) {
        this.mainid = mainid;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone == null ? null : userphone.trim();
    }

    public String getFriendphone() {
        return friendphone;
    }

    public void setFriendphone(String friendphone) {
        this.friendphone = friendphone == null ? null : friendphone.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}