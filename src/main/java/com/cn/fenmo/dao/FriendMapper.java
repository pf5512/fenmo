package com.cn.fenmo.dao;

import java.util.Map;

import com.cn.fenmo.pojo.Friend;
public interface FriendMapper {
    int deleteByPrimaryKey(Long mainid);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Long mainid);
    
    Friend selectBy(Map<String,Object> params);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);
    
    int deleteFriend(Map<String,Object> params);
    
    int updateNickName(Map<String,Object> params);
    
    int updateFriendNickName(Map<String,Object> params);
    
    

}