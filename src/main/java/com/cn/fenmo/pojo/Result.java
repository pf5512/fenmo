package com.cn.fenmo.pojo;

import java.io.Serializable;

public class Result<T> implements Serializable{
  
  public T data;
  public String message;
  public int code;
  
  public Result(T dataT, String message, int code) {
    this.data = dataT;
    this.message = message;
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setDataT(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
  
  
  
  
  
  
  
  

}
