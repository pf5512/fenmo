package com.cn.fenmo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.NewsComment;
import com.cn.fenmo.pojo.NewsCommentUser;


@Service
public interface NewsCommentService {
 
  public boolean save(NewsComment bean);
  
  public boolean delete(long mainId);
  
  public boolean updateZcount(long mainId);

  public NewsComment getBeanById(long mainId);
  
  public int getNewsComentCount(Map<String, Object> params);
  
  public List<NewsCommentUser> getNewsComentPage(Map<String, Object> params);
  
}
