package com.cn.fenmo.util.maputil.impl;

/**
 * 
 * @description long 类型的值
 * @author weiwj
 * @date 下午10:12:10
 */
public class LongFieldValue extends LongObjFieldValue{
	@Override
	public String getType() {
		return "long";
	}
	
	@Override
	public Long translateValue(Object value) {
		Long v = super.translateValue(value);
		if(v == null){
			return 0L;
		}
		return v;
	}
}
