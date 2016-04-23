package com.cn.fenmo.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cn.fenmo.pojo.UserBean;

public class RequestUtil {
	
	public static final String  HTTPHEAD = "http://"; 
	
	/**
	 * 
	 * @Description:获取当前登录用户信息
	 * @param reqeust
	 * @return
	 * @return UserBean
	 * @throws
	 */
	public static UserBean getLoginUser(HttpServletRequest reqeust){
		UserBean userBean = (UserBean)reqeust.getSession().getAttribute("fmUser");
		return userBean;
	}
	
	
	/**
	 * 
	 * @description 获取查询参数，存入Map。 注意，得到的值都是String类型。
	 * @author weiwj
	 * @date 下午8:56:40
	 * @param request
	 * @return
	 */
	public static Map<String,Object> getRequestParamMap(HttpServletRequest request){
		
		 Enumeration<String> names = request.getParameterNames();
		 
		 Map<String,Object> param = new HashMap<String, Object>();
		 
		 while(names.hasMoreElements()){
			 String name = names.nextElement();
			 String value = request.getParameter(name);//暂时只考虑参数不重复的情况 TODO
			 if(value == null || value.length() == 0){
				 continue;
			 }
			 if("page".equals(name)){
				 param.put(name, Integer.valueOf(value)-1);//分页时开始页从0开始
			 }else if("rows".equals(name)){
			   param.put(name, Integer.valueOf(value));
			 }else{
				 param.put(name, value!=null?value:"");
			 }
		 }
		return param;
	}
	
	/**
	 * 从request中获取参数值
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getValue(HttpServletRequest request,String name){
      if(request ==null || name == null){
          return null;
      }
      String charSet = null;
      String method = request.getMethod();
      method = method == null?"":method;
      String charsetName = request.getCharacterEncoding();
      if(charsetName == null){
          charsetName = "ISO-8859-1";
      }
      if(method.toLowerCase().equals("get")){
          
      }else{//post 请求
        String contentType = request.getHeader("Content-Type") ;
        if(contentType.contains("application/x-www-form-urlencoded")){
          charsetName = "ISO-8859-1";
        }else{
          charsetName="utf-8";
        }
        charSet ="utf-8";
      }
      Object currentCharset = request.getAttribute("current_charset");
      if(currentCharset == null){
        try {
          request.setCharacterEncoding(charSet);
          request.setAttribute("current_charset", charSet);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
      String value = request.getParameter(name);
      
      if(method.toLowerCase().equals("get")){//处理get请求
        
      }else{//处理post请求
          if(!"utf-8".equals(charsetName)){
            value = decode(value,charsetName , charSet);
          }
      }
  
      return value;
	}
	
	
  	private static String decode(String v, String srcCharSet,String distCharSet){
      if (v == null) {
        return null;
      } else {
        try {
          String v1 = convertEncode(v, srcCharSet, distCharSet);
          if (v1 != null && v1.trim().length() > 0) {
            if (distCharSet.equals("utf-8")) {
              String temp = new String( org.apache.commons.codec.binary.Hex.encodeHex(v1.getBytes("utf-8"))).replaceAll("c2a0", "20");
              v1 = new String(org.apache.commons.codec.binary.Hex.decodeHex(temp .toCharArray()), "utf-8");
            }
            return v1;
          } else {
            return null;
          }
        } catch (Exception e) {
        }
        return null;
      }
    }
  	
  	private static String convertEncode(String v, String srcCharSet,String distCharSet) throws UnsupportedEncodingException {
  	  byte[] bytes = v.getBytes(srcCharSet);
  	  return new String(bytes,distCharSet);
    }

	/**
	 * 
	 * @description 从request中生成bean对象 
	 * @author weiwj
	 * @date 下午11:39:54
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T extends Object>  T getBean(HttpServletRequest request,Class<T> clazz) throws Exception{
		return BeanUtil.createBean(getRequestParamMap(request), clazz) ;
	}
	
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
	}
}
