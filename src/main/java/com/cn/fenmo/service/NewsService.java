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
  
  public List<News> selectBeanBy(Map<String,Object> params);
  
  public int selectCount(Map<String,Object> params);
  
  public boolean PublishNews(Map<String,Object> params);
  
  /**
   * 
   * @description 点赞
   * @date  下午8:04:38
   * @author Administrator
   * @param mainId
   * @return
   */
  public boolean updateZCount(long mainId);
  
  //查找新闻表中最新的新闻
  public List<News> getNewsHeadPage(Map<String,Object> params);
  
    /**
   * 
   * @Description: 获取新闻详情
   * @author weiwj	
   * @date 2016-4-9 上午11:28:34	
   * @param mainId
   * @return
   */
  public News selectByPrimaryKey(Long mainId);

  
  /**
   * 
   * @Description:更新新闻内容
   * @author weiwj	
   * @date 2016-4-9 下午5:18:35	
   * @param news
   * @return
   */
  public boolean updateNews(News news);
  
}
