package com.cn.fenmo.dao;

import com.cn.fenmo.pojo.Filelist;

public interface FilelistMapper {

    int insert(Filelist record);

    int insertSelective(Filelist record);

    Filelist getFileInfo(long fileid);
}