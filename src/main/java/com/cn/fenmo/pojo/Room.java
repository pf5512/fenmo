package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cn.fenmo.util.DateUtil;
import com.cn.fenmo.util.RoomCnst;
import com.cn.fenmo.util.StringUtil;

public class Room implements Serializable {
    private Long mainid;

    private String userName;

    private String roomName;

    private String description;
    
    private String subject;

    private Integer type;
    
    private String typeStr;

    private Integer ispublic;
    
    private String ispublicStr;

    private Integer maxusers;

    private String createdate;

    private String headImgePath;

    private String groupId;

    private Integer userCounts;

    private Integer membersonly;
    
    private String membersonlyStr;
    
    private String bjImgUrl;

    private static final long serialVersionUID = 1L;
    
    public String getBjImgUrl() {
      return bjImgUrl;
    }

    public void setBjImgUrl(String bjImgUrl) {
      this.bjImgUrl = bjImgUrl;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }

    public Long getMainid() {
        return mainid;
    }

    public void setMainid(Long mainid) {
        this.mainid = mainid;
    }
    
    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public String getRoomName() {
      return roomName;
    }

    public void setRoomName(String roomName) {
      this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public String getTypeStr() {
      return typeStr;
    }

    public void setTypeStr(int type) {
      this.typeStr = RoomCnst.getTypeStr(type);
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }
    
    public String getIspublicStr() {
      return ispublicStr;
    }

    public void setIspublicStr(int ispublic) {
      this.ispublicStr = RoomCnst.getIsPublicStr(ispublic);
    }

    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public String getCreatedate() {
      return createdate;
    }

    public void setCreatedate(Date createdate) {
      this.createdate =  DateUtil.getDateSecondDashFormat(createdate);
    }

    public String getHeadImgePath() {
      return headImgePath==null?"":headImgePath;
    }

    public void setHeadImgePath(String headImgePath) {
      this.headImgePath = headImgePath==null?"":headImgePath;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getUserCounts() {
      return userCounts;
    }

    public void setUserCounts(Integer userCounts) {
      this.userCounts = userCounts;
    }

    public String getMembersonlyStr() {
      return membersonlyStr;
    }

    public void setMembersonlyStr(int membersonly) {
      this.membersonlyStr = RoomCnst.getMemberonlyStr(membersonly);
    }

    public Integer getMembersonly() {
      return membersonly;
    }

    public void setMembersonly(Integer membersonly) {
        this.membersonly = membersonly;
    }
}