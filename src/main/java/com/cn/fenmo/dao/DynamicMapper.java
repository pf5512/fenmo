package com.cn.fenmo.dao;

import java.util.List;
import java.util.Map;

import com.cn.fenmo.pojo.Dynamic;
import com.cn.fenmo.pojo.DynamicAndUser;

public interface DynamicMapper {

    int deleteByPrimaryKey(Long mainId);

    int insert(Dynamic record);

    Dynamic selectByPrimaryKey(long mainId);
    
    Dynamic selectBy(Map<String, Object> params);
    
    List<? extends Dynamic> selectPage(Map<String, Object> params);
   
    int selectCount(Map<String, Object> params);
    
    int selectDtCount(Map<String, Object> params);
    
    List<DynamicAndUser> selectDtPageBy(Map<String, Object> params);

    
    int updateZCount(Map<String, Object> params);
}