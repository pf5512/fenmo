package com.cn.fenmo.util;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * 
	 * @description 获取src包下面的所有类。不包括子包中的类。 
	 * @author weiwj
	 * @date 下午11:07:47
	 * @param packageName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getSrcPackageClasses(String packageName) throws Exception{
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		URL url = loader.getResource(packageName.replace(".", File.separator));
		
		List<String> classNames = new ArrayList<String>();
		
		if(url != null){
			
			File file = new File(URLDecoder.decode( url.getPath(), "utf-8"));
			
			File[] childFiles = file.listFiles(new FileFilter() {
				public boolean accept(File cFile) {
					return cFile.getName().endsWith(".class");
				}
			});
			
			if(childFiles != null){
				 for (int i = 0; i < childFiles.length; i++) {
					File childFile = childFiles[i];
					classNames.add(packageName+"."+childFile.getName());
				}
			}
		}
		return classNames;
	}

}
