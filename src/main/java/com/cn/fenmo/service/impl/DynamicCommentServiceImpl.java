package com.cn.fenmo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.fenmo.dao.DynamicCommentMapper;
import com.cn.fenmo.pojo.DynamicComment;
import com.cn.fenmo.pojo.DynamicCommentAndUser;
import com.cn.fenmo.service.DynamicCommentService;

@Service("dynamicCommentService")
public class DynamicCommentServiceImpl implements DynamicCommentService{
  @Autowired
  private DynamicCommentMapper dynamicCommentMapper;

  public boolean save(DynamicComment bean) {
    bean.setCreatedate(new Date());
    return this.dynamicCommentMapper.insert(bean)==1?true:false;
  }

  public int getDtComentCount(Map<String, Object> params) {
    return this.dynamicCommentMapper.selectCount(params);
  }

  public List<DynamicCommentAndUser> getDtComentPage(Map<String, Object> params) {
    return  this.dynamicCommentMapper.selectPage(params);
  }

  public DynamicComment getBeanById(long mainId) {
    return this.dynamicCommentMapper.selectByPrimaryKey(mainId);
  }

  public boolean delete(long mainId) {
    return this.dynamicCommentMapper.deleteByPrimaryKey(mainId)==1?true:false;
  }

}
