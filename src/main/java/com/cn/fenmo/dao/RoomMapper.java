package com.cn.fenmo.dao;

import java.util.List;
import java.util.Map;

import com.cn.fenmo.pojo.Room;

public interface RoomMapper {

    int deleteByPrimaryKey(Long mainid);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(Long mainid);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);
    
    List<Room> selectByName(String name);
    
    int selectCount(Map<String, Object> parmars);
    
    List<Room> selectPageBy(Map<String, Object> parmars);

   	int updateByGroupIdSelective(Room room);
   	
   	int updateByGroupNameSelective(Room room);
   
   	Room selectByGroupId(String groupId);
   
   	void deleteByGroupId(String groupId);
}