package com.cn.fenmo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.RoomUsers;
@Service
public interface RoomUsersService {
  
  //���Ⱥ��Ա
  public boolean save(RoomUsers bean);
  
  //ɾ��Ⱥ��Ա
  public boolean deleteRoomUser(String groupId,String userName);
  
  
  public List<String> getRoomUsers(String groupId);
  
  public boolean addBatchRecord(List<RoomUsers> list);
  
}
