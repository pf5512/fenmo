package com.cn.fenmo.util;

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
}
