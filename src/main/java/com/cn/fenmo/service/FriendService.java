package com.cn.fenmo.service;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.Friend;
@Service
public interface FriendService {
  public boolean save(Friend bean);
  
  public Friend getFreind(String userPhone,String friendPhone);
  
  public boolean update(Friend bean);
  
  public boolean deleteFriend(String userPhone,String friendPhone);
  
}
