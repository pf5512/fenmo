package com.cn.fenmo.util.maputil.impl;

/**
 * 
 * @description int 类型的值
 * @author weiwj
 * @date 下午10:11:17
 */
public class IntFieldValue extends IntegerFieldValue{

	public String getType() {
		return "int";
	}
	
	@Override
	public Integer translateValue(Object value) {
		Integer v = super.translateValue(value);
		if(v == null){
			return 0;
		}
		return v;
	}
}
