package com.cn.fenmo.util.maputil;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.fenmo.util.PathUtil;

public class FieldFactory {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FieldFactory.class);
	
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
		
		LOGGER.info("类型为"+clazz.getName()+"的取值类没有实现，可能会造成转换异常，请尽快实现！");
		return null;
	}
}
