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
  
  //����userId��ȡ��Ӧ��userbean
  public List<UserBean> getUserList(List<String> userIdList);
  
  //����10KM�ڵ��û�
  public List<UserBean> getNearUsers(Map<String, Object> params);
  
  //����userPhoneList������ȡuser
  public List<UserBean> getUserListByUserPhoneList(List<String> userPhoneList);
  
  public int selectMyFriendCountBy(Map<String,Object> params);
  //�����ҵĺ���
  public List<UserBean> searchMyfriend(Map<String,Object> params);
  
  //����Ⱥ���еĳ�Ա
  public List<UserBean> getRoomMembers(String groupId);
  
  //����redis�е�userBean 
  public UserBean getUserBeanByUserPhone(String userPhone);
  
  //����fmNo�����û� 
  public UserBean getUserBeanByFmNo(String fmNo);
  
  //����startLevel��ȡ�û��б�
  public List<UserBean> getUsersByStarLevel(int starLevel);
 
 }
