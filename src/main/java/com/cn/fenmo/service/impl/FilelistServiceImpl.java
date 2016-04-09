package com.cn.fenmo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.FilelistMapper;
import com.cn.fenmo.pojo.Filelist;
import com.cn.fenmo.service.FilelistService;


@Service("filelistService")
public class FilelistServiceImpl implements FilelistService {
  
  @Autowired
  private FilelistMapper filelistMapper;

  public int insert(Filelist record) {
    return this.filelistMapper.insert(record);
  }

  public int insertSelective(Filelist record) {
    return   this.filelistMapper.insertSelective(record);
  }

  public Filelist getFileInfo(long fileid) {
    return this.filelistMapper.getFileInfo(fileid);
  }

}
