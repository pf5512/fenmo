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

  //��ȡĳ���˵����к���
  List<UserBean> selectMyFriend(Map<String, Object> parmars);

  List<UserBean> selectUserList(List<String> userIdList);
  
  List<UserBean> selectUserByUserPhoneList(List<String> userPhoneList);
  
  //���ؼ���ģ�������ҵ�����
  List<UserBean> selectMyFriendBy(Map<String,Object> params);
  //��ȡȺ���еĳ�Ա
  List<UserBean> selectMembers(String groupId);
  
  
  
}