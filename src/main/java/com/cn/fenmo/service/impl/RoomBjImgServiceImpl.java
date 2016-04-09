package com.cn.fenmo.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.RoomBjImgMapper;
import com.cn.fenmo.pojo.RoomBjImg;
import com.cn.fenmo.service.RoomBjImgService;


@Service("roomBjImgService")
public class RoomBjImgServiceImpl implements RoomBjImgService{
  @Autowired
	private RoomBjImgMapper roomBjImgMapperMapper;
  
  public boolean save(RoomBjImg bean) {
    return this.roomBjImgMapperMapper.insert(bean)==1?true:false;
  }

  public RoomBjImg getBean(String userName,String groupId) {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("groupId", groupId);
    params.put("userName", userName);
    return this.roomBjImgMapperMapper.selectBy(params);
  }

  public boolean update(RoomBjImg bean) {
    return this.roomBjImgMapperMapper.update(bean)==1?true:false;
  }

}
