package com.cn.fenmo.util;

import java.lang.reflect.Field;
import java.util.Map;

import com.cn.fenmo.util.maputil.MapUtil;

public class BeanUtil {

	/**
	 * 
	 * @description 根据param中的字段值和类名，生成对象
	 * @author weiwj
	 * @date 下午9:01:24
	 * @param param
	 * @param clazz
	 * @return
	 * @throws Exception 
	 */
	public static <T extends Object>  T createBean(Map<String,Object> param,Class<T> clazz) throws Exception{
		
		T obj = clazz.newInstance();
		
		Field[] fields = clazz.getDeclaredFields();
		
		if(fields != null){
			//设置字段的值
			for (int i = 0; i < fields.length; i++) {
				
				Field field = fields[i];
				
				field.setAccessible(true);
				
				String fieldName = field.getName();
				
				Class<?> fieldType = field.getType();
				
				Object value  = MapUtil.getValue(param, fieldName, fieldType);
				
				field.set(obj, value);
			}
		}
		return obj;
	}

}
