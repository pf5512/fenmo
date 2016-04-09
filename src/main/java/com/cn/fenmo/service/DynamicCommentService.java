package com.cn.fenmo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.DynamicComment;
import com.cn.fenmo.pojo.DynamicCommentAndUser;
@Service
public interface DynamicCommentService {
  
  public DynamicComment getBeanById(long mainId);
  
  public boolean save(DynamicComment bean);
  
  public boolean delete(long mainId);
  
  public int getDtComentCount(Map<String,Object> params);
  
  public List<DynamicCommentAndUser> getDtComentPage(Map<String,Object> params);
}
