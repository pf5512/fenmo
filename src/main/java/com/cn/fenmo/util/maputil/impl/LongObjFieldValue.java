package com.cn.fenmo.util.maputil.impl;

import com.cn.fenmo.util.maputil.FieldValue;

/**
 * 
 * @description Long 类型的值 
 * @author weiwj
 * @date 下午10:15:09
 */
public class LongObjFieldValue implements FieldValue{

	public String getType() {
		return "java.lang.Long";
	}


	public Long translateValue(Object value) {
		return value == null ? null:Long.valueOf(String.valueOf(value));
	}

}
