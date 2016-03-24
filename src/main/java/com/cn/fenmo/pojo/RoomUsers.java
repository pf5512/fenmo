package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

public class RoomUsers implements Serializable {
    private Long mainid;

    private String groupId;

    private String userName;

    private Date startdate;
    
    private String userRemark;

    private static final long serialVersionUID = 1L;

    public Long getMainid() {
        return mainid;
    }

    public void setMainid(Long mainid) {
        this.mainid = mainid;
    }
     
    public String getGroupId() {
      return groupId;
    }

    public void setGroupId(String groupId) {
      this.groupId = groupId;
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getUserRemark() {
      return userRemark;
    }

    public void setUserRemark(String userRemark) {
      this.userRemark = userRemark;
    }

}