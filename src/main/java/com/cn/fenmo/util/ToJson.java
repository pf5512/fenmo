package com.cn.fenmo.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class ToJson {
  public void toArrayJson(HttpServletResponse response,List<?> list){
    response.setContentType("text/html;charset=utf-8");
    JSONObject jsonObj = new JSONObject();
    PrintWriter out = null;
    try {
      out = response.getWriter();
      jsonObj.put("code",200);
      jsonObj.put("dataList",list);
      out.println(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    } finally{
      out.close();
    }
  }
  
  public void toJson(HttpServletResponse response,Object bean){
    response.setContentType("text/html;charset=utf-8");
    JSONObject jsonObj = new JSONObject();
    PrintWriter out = null;
    try {
      out = response.getWriter();
      jsonObj.put("code",200);
      jsonObj.put("bean",bean);
      out.println(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      out.close();
    };
  }
  
  
  public void toViewPageForWeb(HttpServletResponse response,List<?> list,int total){
    response.setContentType("text/html;charset=utf-8");
    JSONObject jsonObj = new JSONObject();
    PrintWriter out = null;
    try {
      out = response.getWriter();
      jsonObj.put("code",200);
      jsonObj.put("rows",list);
      jsonObj.put("total",total);
      out.println(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      out.close();
    };
  }
  
  
  public void toViewPage(HttpServletResponse response,ViewPage page){
    response.setContentType("text/html;charset=utf-8");
    JSONObject jsonObj = new JSONObject();
    PrintWriter out = null;
    try {
      out = response.getWriter();
      jsonObj.put("code",200);
      jsonObj.put("viewPage",page);
      out.println(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      out.close();
    };
  }
  
  public void toExMsg(HttpServletResponse response,String str){
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = null;
    JSONObject jsonObj = new JSONObject();
    try {
      out = response.getWriter();
      jsonObj.put("code",201);
      jsonObj.put("message",str);
      out.println(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    } finally{
      out.close();
    }
  }
  
  public void toExSucc(HttpServletResponse response,boolean isSuccess){
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = null;
    JSONObject jsonObj = new JSONObject();
    try {
      out = response.getWriter();
      jsonObj.put("code",200);
      jsonObj.put("message",isSuccess);
      out.println(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    } finally{
      out.close();
    }
  }
  
  public void toExSuccMsg(HttpServletResponse response,String str){
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = null;
    JSONObject jsonObj = new JSONObject();
    try {
      out = response.getWriter();
      jsonObj.put("code",200);
      jsonObj.put("message",str);
      out.println(jsonObj);
    } catch (IOException e) {
      e.printStackTrace();
    } finally{
      out.close();
    }
  }
  
  
  public void toJSON(HttpServletResponse response,Object bean){
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
      Object obj = JSONObject.toJSON(bean);
      out.println(obj);
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      out.close();
    };
  }
}
