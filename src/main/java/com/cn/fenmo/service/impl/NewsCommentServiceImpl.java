package com.cn.fenmo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.NewsCommentMapper;
import com.cn.fenmo.pojo.NewsComment;
import com.cn.fenmo.pojo.NewsCommentUser;
import com.cn.fenmo.service.NewsCommentService;


@Service("newsCommentService")
public class NewsCommentServiceImpl implements NewsCommentService{
 
  @Autowired
  private NewsCommentMapper newsCommentMapper;
  
  public NewsComment getBeanById(long mainId) {
    return this.newsCommentMapper.selectByPrimaryKey(mainId);
  }
  
  public boolean save(NewsComment bean) {
    return this.newsCommentMapper.insert(bean)==1?true:false;
  }

  public boolean delete(long mainid) {
    return this.newsCommentMapper.deleteByPrimaryKey(mainid)==1?true:false;
  }

  public int getNewsComentCount(Map<String, Object> params) {
    return this.newsCommentMapper.selectCount(params);
  }

  public List<NewsCommentUser> getNewsComentPage(Map<String, Object> params) {
    return this.newsCommentMapper.selectPage(params);
  }

}
