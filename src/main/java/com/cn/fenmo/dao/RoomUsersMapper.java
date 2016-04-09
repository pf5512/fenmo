package com.cn.fenmo.dao;

import java.util.HashMap;
import java.util.List;

import com.cn.fenmo.pojo.RoomUsers;

public interface RoomUsersMapper {
    
    int deleteOneUserOfRoom(String groupId,String userName);

    int insert(RoomUsers record);
    
    int updateUserRemark(RoomUsers record);

    RoomUsers selectBy(HashMap<String, Object> params);
    
    int insertBatchRecord(List<RoomUsers>  list);
}


