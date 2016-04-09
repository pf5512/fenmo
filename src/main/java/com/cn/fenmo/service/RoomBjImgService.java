package com.cn.fenmo.service;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.RoomBjImg;
@Service
public interface RoomBjImgService {
  
  public boolean save(RoomBjImg bean);
  
  public RoomBjImg getBean(String groupId,String userName);
  
  public boolean update(RoomBjImg bean);
  
  
}
