package com.cn.fenmo.util;

import java.util.UUID;

/**
 * 文件名: UUIDUtil 项目工程名: 系统通用模块 
 * 描述: UUID工具
 * JDK版本:jdk 1.60+
 * @category 基础类
 * @author ray 日期: 2014-7-1
 * @version v 1.0
 */
public class UUIDUtil {
	public static void main(String arg[]){
		createUUID();
	}
	/**
	 * 生成uuid(20位)
	 * @return
	 */
	public static String createUUID(){
		String uuid="";
		int index = (int) Math.round(Math.random()*10);
		uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(index, index+20);
		return uuid;
	}
}
