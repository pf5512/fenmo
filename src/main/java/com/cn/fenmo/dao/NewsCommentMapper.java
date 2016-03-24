package com.cn.fenmo.dao;

import java.util.List;
import java.util.Map;

import com.cn.fenmo.pojo.NewsComment;
import com.cn.fenmo.pojo.NewsCommentUser;

public interface NewsCommentMapper {
  
  int deleteByPrimaryKey(Long mainid);

  int selectCount(Map<String,Object> params);
  
  List<NewsCommentUser> selectPage(Map<String,Object> params);
  
  int insert(NewsComment record);

  NewsComment selectByPrimaryKey(Long mainid);

}