package com.cn.fenmo.util.maputil.impl;

import com.cn.fenmo.util.maputil.FieldValue;

/**
 * 
 * @description String 类型的值 
 * @author weiwj
 * @date 下午10:15:25
 */
public class StringFieldValue implements FieldValue{

	public String getType() {
		return "java.lang.String";
	}


	public String translateValue(Object value) {
		return value == null ? null:String.valueOf(value);
	}

}
