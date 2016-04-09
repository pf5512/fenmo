package com.cn.fenmo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.FriendMapper;
import com.cn.fenmo.pojo.Friend;
import com.cn.fenmo.service.FriendService;


@Service("friendService")
public class FriendServiceImpl implements FriendService {
  @Autowired
  private FriendMapper friendMapper;
  
  public boolean save(Friend bean) {
    return this.friendMapper.insert(bean)==1?true:false;
  }
  
  public Friend getFreind(String userPhone, String friendPhone) {
    Map<String, Object> params =  new HashMap<String, Object>();
    params.put("userPhone", userPhone);
    params.put("friendPhone", friendPhone);
    return this.friendMapper.selectBy(params);
  }
  
  public boolean update(Friend bean) {
    return this.friendMapper.updateByPrimaryKeySelective(bean)==1?true:false;
  }
  
  public boolean deleteFriend(String userPhone,String friendPhone) {
    Map<String, Object> params =  new HashMap<String, Object>();
    params.put("userPhone", userPhone);
    params.put("friendPhone", friendPhone);
    return this.friendMapper.deleteFriend(params)==1?true:false;
  }

}
