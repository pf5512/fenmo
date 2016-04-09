package com.cn.fenmo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.UserBeanMapper;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.util.StringUtil;

@Service("userService")
public class UserServiceImpl implements IUserService {
  @Autowired
  private UserBeanMapper userBeanMapper;
	
	public UserBean getUserById(long userId) {
		return this.userBeanMapper.selectByPrimaryKey(userId);
  }
  public UserBean getUserBeanByPhone(String phone) {
    return this.userBeanMapper.selectByPhone(phone);
  }
  public int selectCount(Map<String,Object> params) {
    return this.userBeanMapper.selectCount(params);
  }
  public List<UserBean> selectUserBy(Map<String, Object> params) {
    return this.userBeanMapper.selectPageBy(params);
  }
  public boolean save(UserBean userBean) {
    return this.userBeanMapper.insert(userBean)==1?true:false;
  }
  public int update(UserBean userBean) {
    return this.userBeanMapper.update(userBean);
  }

  public UserBean getUserByToken(String token) {
    return this.userBeanMapper.selectByToken(token);
  }
  public int selectMyFriendCount(Map<String,Object> params) {
    return this.userBeanMapper.selectMyFriendCount(params);
  }
  public List<UserBean> getMyFriend(Map<String,Object> params) {
    return this.userBeanMapper.selectMyFriend(params);
  }
  public List<UserBean> getUserList(List<String> userIdList) {
    return this.userBeanMapper.selectUserList(userIdList);
  }
  public List<UserBean> searchMyfriend(Map<String, Object> params) {
    return this.userBeanMapper.selectMyFriendBy(params);
  }
  public int selectMyFriendCountBy(Map<String, Object> params) {
    return this.userBeanMapper.selectMyFriendCountBy(params);
  }
  public List<UserBean> getRoomMembers(String groupId) {
    return this.userBeanMapper.selectMembers(groupId);
  }
  
  public UserBean getUserBeanByUserPhone(String userPhone) {
    String token = (String) RedisClient.get(userPhone);
    UserBean bean = null;
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }else{
      bean = this.getUserBeanByPhone(userPhone);
      if(bean!=null){
        RedisClient.set(userPhone, token);
        RedisClient.setObject(bean.getToken(), bean);
      }
    }
    return bean;
  }
  
  public UserBean getUserBeanByFmNo(String fmNo) {
    return  this.userBeanMapper.selectByFmNo(fmNo);
  }
  public List<UserBean> getUserListByUserPhoneList(List<String> userPhoneList) {
    return this.userBeanMapper.selectUserByUserPhoneList(userPhoneList);
  }
  public List<UserBean> getNearUsers(Map<String, Object> params) {
    return this.userBeanMapper.selectNearList(params);
  }
  public int updateLocation(Map<String, Object> params) {
    return this.userBeanMapper.updateLocation(params);
  }
  public List<UserBean> getUsersByStarLevel(int starLevel) {
    return this.userBeanMapper.selectUsersByStarLevel(starLevel);
  }
  public List<UserBean> selectMyFriendRequest(Map<String, Object> params) {
    return this.userBeanMapper.selectMyFriendRequest(params);
  }
}
