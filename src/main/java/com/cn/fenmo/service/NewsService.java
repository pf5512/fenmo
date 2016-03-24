package com.cn.fenmo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.News;


@Service
public interface NewsService {
  public News getBeanById(long mainId);
  
  public boolean save(News bean);
  
  public boolean delete(long mainId);
  
  public List<? extends News> selectBeanBy(Map<String,Object> params);
  
  public int selectCount(Map<String,Object> params);
  
  public boolean PublishNews(Map<String,Object> params);
  
  public boolean updateZCount(long mainId);
  
}
