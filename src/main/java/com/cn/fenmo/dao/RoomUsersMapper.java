package com.cn.fenmo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.fenmo.pojo.RoomUsers;

public interface RoomUsersMapper {
    
    int deleteOneUserOfRoom(Map<String, Object> parmas);

    int insert(RoomUsers record);
    
    int updateUserRemark(RoomUsers record);

    RoomUsers selectBy(HashMap<String, Object> params);
    
    int insertBatchRecord(List<RoomUsers>  list);
}


