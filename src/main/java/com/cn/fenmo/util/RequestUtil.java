package com.cn.fenmo.util;

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
			 
			 if("page".equals(name)||"rows".equals(name)){
				 
				 param.put(name, Integer.valueOf(value));
				 
			 }else{
				 
				 param.put(name, value);
			 }
		 }
		return param;
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
