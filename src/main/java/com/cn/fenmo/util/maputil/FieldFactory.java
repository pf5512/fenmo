package com.cn.fenmo.util.maputil;

import java.lang.reflect.Method;
import java.util.List;

import com.cn.fenmo.util.PathUtil;

public class FieldFactory {
	
	/**
	 * 
	 * @description 获取字段值对象 
	 * @author weiwj
	 * @date 下午10:32:05
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static  FieldValue createFieldValue(Class<?> clazz) throws Exception {
		
		String type = clazz.getName();
		
		String packageName = "com.cn.fenmo.util.maputil.impl";
		
		List<String> classNames = PathUtil.getSrcPackageClasses(packageName);
		
		if(classNames.size() > 0){
			
			for (String className : classNames) {
				
				Class<?> c = Class.forName(className);
				
				String methodName = "getType";
				
				Method method = c.getDeclaredMethod(methodName);
				
				method.setAccessible(true);
				
				Object obj = c.newInstance(); 
				
				String fileType = (String)method.invoke(obj);
				
				if(type.equals(fileType)){
					
					return (FieldValue)obj;
					
				}else{
					obj = null; //释放对象
				}
			}
		}
		return null;
	}
}
