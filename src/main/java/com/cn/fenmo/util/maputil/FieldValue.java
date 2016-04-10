package com.cn.fenmo.util.maputil;

public interface FieldValue {
	/**
	 * 
	 * @description 获取类型 
	 * @author weiwj
	 * @date 下午9:59:18
	 * @return
	 */
	String getType();
	
	/**
	 * 
	 * @description 对传入的值进行翻译
	 * @author weiwj
	 * @date 下午9:59:30
	 * @return
	 */
	Object translateValue(Object value);
}
