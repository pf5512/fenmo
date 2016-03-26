package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

import com.cn.fenmo.util.DateUtil;

public class DynamicComment implements Serializable {
    private Long mainid;

    private Long dynamicid;

    private String createdate;

    private String  userName;

    private String content;

    private static final long serialVersionUID = 1L;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}