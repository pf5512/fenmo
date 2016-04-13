package com.cn.fenmo.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.util.maputil.MapUtil;

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
				param.put(name, value);
		 }
		 
		 //分页
		 if(param.containsKey("page")&&param.containsKey("rows")){
			 int rows = Integer.parseInt(String.valueOf(param.get("rows")));
			 int page = Integer.parseInt(String.valueOf(param.get("page")));
			 int start = (page-1)*rows;
			 param.put("start", start);
			 param.put("limit", rows);
			 
		 }
		 
		 //排序
		 if(param.containsKey("order") && param.containsKey("sort")){
			 String orderby = " order by "+String.valueOf(MapUtil.getValue(param, "sort"))+"  "+String.valueOf(MapUtil.getValue(param, "order"));
			 param.put("orderby", orderby);
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
