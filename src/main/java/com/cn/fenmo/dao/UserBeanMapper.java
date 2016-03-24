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

  int updateByPrimaryKey(UserBean record);
  
  int updateByPrimaryKeySelective(UserBean record);
  
  int selectCount(Map<String, Object> parmars);
  
  List<UserBean> selectPageBy(Map<String, Object> parmars);
  
  int selectMyFriendCount(Map<String, Object> parmars);
  
  int selectMyFriendCountBy(Map<String, Object> parmars);

  //获取某个人的所有好友
  List<UserBean> selectMyFriend(Map<String, Object> parmars);

  List<UserBean> selectUserList(List<String> userIdList);
  
  List<UserBean> selectUserByUserPhoneList(List<String> userPhoneList);
  
  //按关键字模糊查找我的朋友
  List<UserBean> selectMyFriendBy(Map<String,Object> params);
  //获取群组中的成员
  List<UserBean> selectMembers(String groupId);
  
  
  
}