package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

public class NewsComment implements Serializable {
    private Long mainid;

    private String content;

    private Long newsid;

    private Date createdate;

    private String userName;

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