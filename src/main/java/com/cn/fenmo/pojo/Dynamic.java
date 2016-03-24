package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

public class Dynamic implements Serializable {
    private Long mainid;

    private String content;
    
    private String imgUrl;

    private String userName;

    private Date createdate;
    
    private int zcount;
    
    private int state;

    private static final long serialVersionUID = 1L;

    public String getImgUrl() {
      return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
    }

    public int getZcount() {
      return zcount;
    }

    public void setZcount(int zcount) {
      this.zcount = zcount;
    }

    public int getState() {
      return state;
    }

    public void setState(int state) {
      this.state = state;
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
 
}