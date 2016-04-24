package com.cn.fenmo.dao;

import java.util.List;
import java.util.Map;

import com.cn.fenmo.pojo.News;

public interface NewsMapper {
    int deleteByPrimaryKey(Long mainId);

    int insert(News record);
    
    int insertBatchRecord(List<News> list);

    News selectByPrimaryKey(Long mainId);
    
    
    News selectByUniqueKey(String httpUrl);

    int update(News record);
    
    int publishNews(Map<String, Object> params);
    
    int selectCount(Map<String, Object> params);
    
    List<News> selectPageBy(Map<String, Object> params);
    
    List<News> selectNewsHeadPage(Map<String, Object> params);
    
    List<News> selectInterfixNews(Map<String, Object> params);
    
    int updateZCount(Map<String, Object> params);
}