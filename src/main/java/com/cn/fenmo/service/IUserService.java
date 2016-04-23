package com.cn.fenmo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.cn.fenmo.pojo.UserBean;
@Service
public interface IUserService {
  public UserBean getUserById(long userId);
  public boolean save(UserBean userBean);
  public int update(UserBean userBean);
  public int updateLocation(Map<String, Object> params);
  public List<UserBean> selectUserBy(Map<String,Object> params);
  public int selectCount(Map<String,Object> params);
  public UserBean getUserBeanByPhone(String phone);
  public UserBean getUserByToken(String token); 
  public List<UserBean> getMyFriend(Map<String,Object> params);
  public int selectMyFriendCount(Map<String,Object> params);
  
  public List<UserBean> selectMyFriendRequest(Map<String,Object> params);
  
  //根据userId获取对应的userbean
  public List<UserBean> getUserList(List<String> userIdList);
  
  //根据10KM内的用户
  public List<UserBean> getNearUsers(Map<String, Object> params);
  
  //根据userPhoneList批量获取user
  public List<UserBean> getUserListByUserPhoneList(List<String> userPhoneList);
  
  public int selectMyFriendCountBy(Map<String,Object> params);
  //查找我的好友
  public List<UserBean> searchMyfriend(Map<String,Object> params);
  
  //查找群组中的成员
  public List<UserBean> getRoomMembers(Map<String,Object> params);
  
  
  //查找redis中的userBean 
  public UserBean getUserBeanByUserPhone(String userPhone);
  
  //根据fmNo查找用户 
  public UserBean getUserBeanByFmNo(String fmNo);
  
  //根据fmNo查找用户 
  public UserBean getFreind(Map<String,Object> parmas);
  
  //根据startLevel获取用户列表
  public List<UserBean> getUsersByStarLevel(int starLevel);
 
 }
