package com.cn.fenmo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.Room;
@Service
public interface IRoomService {
	public Room getRoomById(long roomId);

	public boolean save(Room room);
	public int update(Room room);
	public int updateByGroupIdSelective(Room room);
	public List<? extends Room> selectRooms(Map<String,Object> params);
	public int selectCount(Map<String, Object> params);
	public Room getRoomByGroupId(String groupId);
	
  public Room getMaxUseRoom(String userName);

	public void deleteRoomByGroupId(String groupId);
	
	public List<Room> getHotRooms();
	
	public List<Room> getRooms(Map<String, Object> params);
	
	public List<Room> getRoomByName(String roomName);
	

}
