package com.cn.fenmo.util.maputil.impl;

import com.cn.fenmo.util.maputil.FieldValue;

/**
 * 
 * @description Integer 类型的值
 * @author weiwj
 * @date 下午10:11:55
 */
public class IntegerFieldValue implements FieldValue{

	public String getType() {
		return "java.lang.Integer";
	}


	public Integer translateValue(Object value) {
		
		if(value == null){
			
			return null;
		}
		
		String v = String.valueOf(value);
		
		if(v.trim().length() == 0){
			
			return null;
		}
		
		return Integer.valueOf(v);
	}

}
