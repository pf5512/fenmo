package com.cn.fenmo.pojo;

import java.io.Serializable;
import java.util.Date;

public class Filelist implements Serializable {
    private Long fileid;

    private String filename;

    private Long filelength;

    private Date uploadtime;

    private String fileserver;

    private static final long serialVersionUID = 1L;

    public Long getFileid() {
        return fileid;
    }

    public void setFileid(Long fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Long getFilelength() {
        return filelength;
    }

    public void setFilelength(Long filelength) {
        this.filelength = filelength;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getFileserver() {
        return fileserver;
    }

    public void setFileserver(String fileserver) {
        this.fileserver = fileserver;
    }
}