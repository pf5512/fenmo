package com.cn.fenmo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.NewsMapper;
import com.cn.fenmo.pojo.News;
import com.cn.fenmo.service.NewsService;


@Service("newsService")
public class NewsServiceImpl implements NewsService{
  @Autowired
  private NewsMapper newsMapper;
  
  public News getBeanById(long mainId) {
    return this.newsMapper.selectByPrimaryKey(mainId);
  }

  public boolean save(News bean) {
    return this.newsMapper.insert(bean)==1?true:false;
  }

  public List<News> selectBeanBy(Map<String, Object> params) {
    List<News> list  = this.newsMapper.selectPageBy(params);
    return list;
  }

  public int selectCount(Map<String, Object> params) {
    return this.newsMapper.selectCount(params);
  }

  public boolean PublishNews(Map<String, Object> params) {
    return this.newsMapper.publishNews(params)==1?true:false;
  }

  public boolean updateZCount(long mainId) {
    News bean = this.newsMapper.selectByPrimaryKey(mainId);
    if(bean==null){
      return false;
    }else {
      int zcount=bean.getZcount()+1;
      Map<String, Object> params =  new HashMap<String, Object>();
      params.put("zcount", zcount);
      params.put("mainId", mainId);
      if(this.newsMapper.updateZCount(params)!=1){
        return false;
      }
    }
    return true;
  }

  public boolean delete(long mainId) {
    return this.newsMapper.deleteByPrimaryKey(mainId)==1?true:false;
  }

  public List<News> getNewsHeadPage(Map<String, Object> params) {
    return this.newsMapper.selectNewsHeadPage(params);
  }

	public News selectByPrimaryKey(Long mainId) {
		return this.newsMapper.selectByPrimaryKey(mainId);
	}
	

  public News selectByUniqueKey(String newsHttpUrl) {
    return this.newsMapper.selectByUniqueKey(newsHttpUrl);
  }

	public boolean updateNews(News news) {
		return this.newsMapper.update(news)==1?true:false;
	}

  public List<News> getInterfixNews(Map<String, Object> params) {
    return this.newsMapper.selectInterfixNews(params);
  }

  public int addAatchNews(List<News> list) {
    return this.newsMapper.insertBatchRecord(list);
  }

  

}
