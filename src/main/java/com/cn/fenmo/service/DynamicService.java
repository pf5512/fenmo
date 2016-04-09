package com.cn.fenmo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cn.fenmo.pojo.Dynamic;
import com.cn.fenmo.pojo.DynamicAndUser;
@Service
public interface DynamicService {
  public Dynamic getBeanById(long mainId);
  /*获取一个已经发布的动态*/
  public Dynamic getBeanBy(Map<String,Object> params);
  
  public boolean save(Dynamic bean);
  
  public boolean delete(long mainId);
  
  public boolean updateZCount(long mainId);
  
  public int selectDtCount(Map<String,Object> params);
  
  public List<DynamicAndUser> getDtPageBy(Map<String,Object> params);
  
}
