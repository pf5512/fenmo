package com.cn.fenmo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.DynamicMapper;
import com.cn.fenmo.pojo.Dynamic;
import com.cn.fenmo.pojo.DynamicAndUser;
import com.cn.fenmo.service.DynamicService;

@Service("dynamicService")
public class DynamicServiceImpl implements  DynamicService {
  
  @Autowired
  private DynamicMapper dynamicMapper;
  
  public Dynamic getBeanById(long mainid) {
    return this.dynamicMapper.selectByPrimaryKey(mainid);
  }


  public boolean save(Dynamic bean) {
    return this.dynamicMapper.insert(bean)==1?true:false;
  }
  
  public  boolean updateZCount(long mainId) {
    Dynamic dynamic = this.dynamicMapper.selectByPrimaryKey(mainId);
    if(dynamic==null){
      return false;
    }else {
      int zcount=dynamic.getZcount()+1;
      Map<String, Object> params =  new HashMap<String, Object>();
      params.put("zcount", zcount);
      params.put("mainId", mainId);
      if(this.dynamicMapper.updateZCount(params)!=1){
        return false;
      }
    }
    return true;
  }
  public Dynamic getBeanBy(Map<String, Object> params) {
    return this.dynamicMapper.selectBy(params);
  }

  public boolean delete(long mainId) {
    return this.dynamicMapper.deleteByPrimaryKey(mainId)==1?true:false;
  }

  public int selectDtCount(Map<String, Object> params) {
    return this.dynamicMapper.selectDtCount(params);
  }

  public List<DynamicAndUser> getDtPageBy(Map<String, Object> params) {
    return  this.dynamicMapper.selectDtPageBy(params);
    
  }
}
