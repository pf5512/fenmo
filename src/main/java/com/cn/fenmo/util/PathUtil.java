package com.cn.fenmo.util;

import java.io.File;
import java.net.URLDecoder;

public class PathUtil {
	
	/**
	 * 
	 * @Description: 获取根目录路径
	 * @author weiwj	
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public static String getRootPath() throws Exception {
		String url = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		url = URLDecoder.decode(url, "utf-8");
		File file = new File(url);
		File webappDir = file.getParentFile().getParentFile();
		String rootPath = webappDir.getPath();
		return rootPath;
	}
	
	/**
	 * 
	 * @Description: 合并文件路径
	 * @author weiwj	
	 * @param names 
	 * @return String
	 * @throws
	 */
	public static String combineFilePath(String[] names){
		if(names != null){
			StringBuilder path = new StringBuilder();
			for (String name : names) {
				if(path.length() > 0){
					path.append(File.separator);
				}
				if(name != null){
					path.append(name);
				}
			}
			return path.toString();
		}
		return "";
	}

}
