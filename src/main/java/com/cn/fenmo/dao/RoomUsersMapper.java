package com.cn.fenmo.dao;

import java.util.List;

import com.cn.fenmo.pojo.RoomUsers;

public interface RoomUsersMapper {
    
    int deleteOneUserOfRoom(String groupId,String userName);

    int insert(RoomUsers record);

    int insertSelective(RoomUsers record);

    RoomUsers selectByPrimaryKey(Long mainid);

    int updateByPrimaryKeySelective(RoomUsers record);

    int updateByPrimaryKey(RoomUsers record);
    
    int insertBatchRecord(List<RoomUsers>  list);
}


