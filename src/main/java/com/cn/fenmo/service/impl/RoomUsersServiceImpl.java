package com.cn.fenmo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.RoomUsersMapper;
import com.cn.fenmo.pojo.RoomUsers;
import com.cn.fenmo.service.RoomUsersService;


@Service("roomUsersService")
public class RoomUsersServiceImpl implements RoomUsersService{
  @Autowired
	 private RoomUsersMapper roomUsersMapper;

  public boolean deleteRoomUser(String groupId,String userName) {
    return this.roomUsersMapper.deleteOneUserOfRoom(groupId,userName)==1?true:false;
  }

  public List<String> getRoomUsers(String roomId) {
    return null;
  }

  public boolean save(RoomUsers bean) {
    return this.roomUsersMapper.insert(bean)==1?true:false;
  }

  public boolean addBatchRecord(List<RoomUsers> list) {
    return this.roomUsersMapper.insertBatchRecord(list)>1?true:false;
  }

}
