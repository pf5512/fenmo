package com.cn.fenmo.util.maputil;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MapUtil {
	
	
	/**
	 * 
	 * @description 从Map中获取参数 
	 * @author weiwj
	 * @date 下午9:34:09
	 * @param map
	 * @param name
	 * @return
	 */
	public static Object getValue(Map<String,Object> map,String name){
		if(map == null){
			return null;
		}
		
		if(name == null){ //key可以为null
			return map.get(name);
		}
			
		Object value = map.get(name);
		
		if(value == null){//如果没取到值，尝试将name转为大写获取值
			name = name.toUpperCase();
			value = map.get(name);
		}
		
		if(value == null){ //如果没取到值，尝试将name转为小写获取值
			name = name.toLowerCase();
			value = map.get(name);
		}
		
		if(value == null){//如果还没取到，则遍历map的所有key进行比较获取
			Iterator<String> it =  map.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				if(key != null && key.toUpperCase().equals(name.toUpperCase())){
					value = map.get(key);
					break;
				}
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @description 根据class类型，将从map获取的值，转为对应类型
	 * @author weiwj
	 * @date 下午9:48:38
	 * @param map
	 * @param name
	 * @param clazz
	 * @return
	 * @throws Exception 
	 */
	public static Object getValue(Map<String,Object> map,String name,Class<?> clazz) throws Exception{
		
		Object value = getValue(map, name);
		
		if(value == null){
			
			return null;
		}
		
		Class<?> vClazz = value.getClass();
		
		if(vClazz.getName().equals(clazz.getName())){
			
			return value;
			
		}else{
			
				return translateValue(value, clazz);
				
		}
		
	}
	
	/**
	 * 
	 * @description 转换值
	 * @author weiwj
	 * @date 上午12:11:11
	 * @param value
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Object translateValue(Object value,Class<?> clazz) throws Exception{
		
		FieldValue fv = FieldFactory.createFieldValue(clazz);
		
		if(fv != null){
			
			return fv.translateValue(value);
			
		}else {//如果没有实现，则直接强转
			
			return clazz.cast(value);
		}
		
	}
}
