package com.cn.fenmo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.taglibs.standard.tag.rt.core.ForEachTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.RoomMapper;
import com.cn.fenmo.pojo.Room;
import com.cn.fenmo.service.IRoomService;


@Service("roomService")
public class RoomServiceImpl implements IRoomService {
  @Autowired
	 private RoomMapper roomMapper;

  public Room getRoomById(long roomId) {
  	return this.roomMapper.selectByPrimaryKey(roomId);
  }
  
  public boolean save(Room room) {
  	return this.roomMapper.insert(room)==1?true:false;
  }
  
  public int update(Room room) {
  	return this.roomMapper.updateByPrimaryKey(room);
  }
  
  public List<? extends Room> selectRooms(Map<String, Object> params) {
  	return this.roomMapper.selectPageBy(params);
  }
  
  public int selectCount(Map<String, Object> params) {
  	return this.roomMapper.selectCount(params);
  }
  
  	public List<Room> getRoomByName(String roomName) {
  		return this.roomMapper.selectByName(roomName);
  	}
  
  	public int updateByGroupIdSelective(Room room) {
  		return this.roomMapper.updateByGroupIdSelective(room);
  	}
  
  
  	public Room getRoomByGroupId(String groupId) {
  		return this.roomMapper.selectByGroupId(groupId);
  	}
  
  	public void deleteRoomByGroupId(String groupId) {
  		this.roomMapper.deleteByGroupId(groupId);
  	}
  
  	public List<Room> getRooms(Map<String, Object> params) {
  	  List<Room> list = this.roomMapper.selectPageBy(params);
  	  return list;
  	}

    public List<Room> getHotRooms() {
      return this.roomMapper.selectHotRooms();
    }

    public Room getMaxUseRoom(String userName) {
      return this.roomMapper.selectRoomMaxUsers(userName);
    }

    public Room getRoomByParams(Map<String, Object> params) {
      return this.roomMapper.selectRoomByParmas(params);
    }

    public List<Room> getMyJoinRoom(Map<String, Object> params) {
      return this.roomMapper.selectMyJoinRoom(params);
    }

    public int getMyJoinRoomCount(Map<String, Object> params) {
      return this.roomMapper.selectMyJoinRoomCount(params);
    }
}
