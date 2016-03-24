package com.cn.fenmo.util;

import java.util.ArrayList;
import java.util.List;

public class ViewPage {
  private int totalCount;
  private int pageStart;
  private int pageLimit;
  private Integer allPages;
  private List listResult;
  
  
  public ViewPage(){
    this.totalCount = 0;
    this.pageStart = 0;
    this.pageLimit = 10;
    this.allPages = null;
    this.listResult = null;
  }

  public int getPageLimit(){
    return this.pageLimit;
  }
  
  public void setPageLimit(int limit){
    if (this.pageLimit <= 0){
      this.pageLimit = 20;
    }else{
      this.pageLimit = limit;
    }
  }

  public int getPageStart() {
    return this.pageStart;
  }
  
  public void setPageStart(int start){
    if (start < 0){
      this.pageStart = 0;
    }else{
      this.pageStart = start;
    }
  }
  
  public int getTotalCount(){
    return this.totalCount;
  }
  
  public void setTotalCount(int totalCount){
    this.totalCount = totalCount;
  }

  public Integer getAllPages() {
    return allPages;
  }

  public void setAllPages(Integer allPages) {
    this.allPages = allPages;
  }

  public List getListResult() {
    if(this.listResult == null){
      return new ArrayList();
    }
    return this.listResult;
  }

  public void setListResult(List listResult) {
    this.listResult = listResult;
  }
  
  
  
}
