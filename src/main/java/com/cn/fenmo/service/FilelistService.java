package com.cn.fenmo.service;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.Filelist;
@Service
public interface FilelistService {
  
  int insert(Filelist record);

  int insertSelective(Filelist record);

  Filelist getFileInfo(long fileid);
}
