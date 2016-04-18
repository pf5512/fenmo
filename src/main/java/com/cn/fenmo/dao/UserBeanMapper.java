package com.cn.fenmo.dao;

import java.util.List;
import java.util.Map;

import com.cn.fenmo.pojo.UserBean;

public interface UserBeanMapper {
  int deleteByPrimaryKey(Long mainId);

  int insert(UserBean record);

  UserBean selectByPrimaryKey(Long mainid);
  
  UserBean selectByPhone(String phone);
  
  UserBean selectByToken(String token);
  
  UserBean selectByFmNo(String fmNo);

  int update(UserBean record);
  
  int selectCount(Map<String, Object> parmars);
  
  List<UserBean> selectPageBy(Map<String, Object> parmars);
  
  List<UserBean> selectMembers(Map<String, Object> parmars);
  
  int selectMyFriendCount(Map<String, Object> parmars);
  
  int selectMyFriendCountBy(Map<String, Object> parmars);

  List<UserBean> selectMyFriend(Map<String, Object> parmars);
  
  List<UserBean> selectMyFriendRequest(Map<String, Object> parmars);

  List<UserBean> selectUserList(List<String> userIdList);
  
  List<UserBean> selectUserByUserPhoneList(List<String> userPhoneList);
  
  List<UserBean> selectMyFriendBy(Map<String,Object> params);
  List<UserBean> selectMembers(String groupId);
  
  List<UserBean> selectNearList(Map<String,Object> params);
  
  int  updateLocation(Map<String, Object> parmars);

  List<UserBean> selectUsersByStarLevel(int starLevel);
  
  
  
}