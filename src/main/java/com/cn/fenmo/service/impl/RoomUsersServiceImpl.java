package com.cn.fenmo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.RoomUsersMapper;
import com.cn.fenmo.pojo.RoomUsers;
import com.cn.fenmo.service.RoomUsersService;

@Service("roomUsersService")
public class RoomUsersServiceImpl implements RoomUsersService{
  @Autowired
  private RoomUsersMapper roomUsersMapper;

  public boolean deleteRoomUser(String groupId,String userPhone) {
    Map<String,Object> parmas = new HashMap<String,Object>();
    parmas.put("groupId", groupId);
    parmas.put("userPhone", userPhone);
    return this.roomUsersMapper.deleteOneUserOfRoom(parmas)==1?true:false;
  }

  public boolean save(RoomUsers bean) {
    return this.roomUsersMapper.insert(bean)==1?true:false;
  }

  public boolean addBatchRecord(List<RoomUsers> list) {
    return this.roomUsersMapper.insertBatchRecord(list)>0?true:false;
  }

  public RoomUsers getRoomUsers(String userName,String groupId) {
    HashMap<String, Object> params =  new HashMap<String, Object>();
    params.put("userName", userName);
    params.put("groupId", groupId);
    return this.roomUsersMapper.selectBy(params);
  }

  public boolean updateRoomUser(RoomUsers bean) {
    return this.roomUsersMapper.updateUserRemark(bean)==1?true:false;
  }
  

}
