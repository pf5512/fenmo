package com.cn.fenmo.dao;

import java.util.List;
import java.util.Map;

import com.cn.fenmo.pojo.DynamicComment;
import com.cn.fenmo.pojo.DynamicCommentAndUser;

public interface DynamicCommentMapper {

    int deleteByPrimaryKey(Long mainid);
   
    int insert(DynamicComment record);

    DynamicComment selectByPrimaryKey(Long mainid);
    
    int selectCount(Map<String, Object> params);
    
    List<DynamicCommentAndUser> selectPage(Map<String, Object> params);

}