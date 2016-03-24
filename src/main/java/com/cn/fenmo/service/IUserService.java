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
 	public List<? extends UserBean> selectUserBy(Map<String,Object> params);
 	public int selectCount(Map<String,Object> params);
 	public UserBean getUserBeanByPhone(String phone);
  public UserBean getUserByToken(String token);
  public List<? extends UserBean> getMyFriend(Map<String,Object> params);
  public int selectMyFriendCount(Map<String,Object> params);
  //����userId��ȡ��Ӧ��userbean
  public List<UserBean> getUserList(List<String> userIdList);
  
  //����10KM�ڵ��û�
  public List<UserBean> getNearUsers(double lat,double lng);
  
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
 
 }
