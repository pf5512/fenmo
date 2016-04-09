package com.cn.fenmo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 文件名: FileUtil 项目工程名: 系统通用模块 
 * 描述: 文件获取操作工具
 * JDK版本:jdk 1.60+
 * @category 基础类
 * @author ray 日期: 2014-7-1
 * @version v 1.0
 */
public class FileUtil {
	/**
	 * 根据路径获取文件 字符串
	 * @param path 文件路径
	 * @return 文件字符串
	 * @throws IOException 
	 */
public static String getFileByPath(String path) throws IOException{
	  String fileString="";
	  BufferedReader reader = null;
		File file=new File(path);
		  reader = new BufferedReader(new FileReader(file));
		  String temp="";
		 while ((temp=reader.readLine())!=null) {
			 fileString+=temp;
         }
	  return fileString;
  }
  
  /**
   * 读取某个文件夹下的所有文件
   */
  public static  List<File> readfile(String filepath) throws FileNotFoundException, IOException {
	          List<File> list= new ArrayList<File>();
                  File file = new File(filepath);
                  if (file.isDirectory()) {
                          String[] filelist = file.list();
                          for (int i = 0; i < filelist.length; i++) {
                                  File readfile = new File(filepath + "\\" + filelist[i]);
                                  if (!readfile.isDirectory()) {
                                          list.add(readfile);
                                  }
                          }
                  }
          return list;
  }

  /**
   * 删除某个文件夹下的所有文件夹和文件
   */
  public static boolean deletefile(String delpath)
                  throws FileNotFoundException, IOException {
                  File file = new File(delpath);
                  if (!file.isDirectory()) {
                          file.delete();
                  } else if (file.isDirectory()) {
                          String[] filelist = file.list();
                          for (int i = 0; i < filelist.length; i++) {
                                  File delfile = new File(delpath + "\\" + filelist[i]);
                                  if (!delfile.isDirectory()) {
                                          delfile.delete();
                                  } else if (delfile.isDirectory()) {
                                          deletefile(delpath + "\\" + filelist[i]);
                                  }
                          }
                          file.delete();
                  }
          return true;
  }

  /**
   * 上传文件到指定文件夹
   * @param file 文件
   * @param filename 文件名
   * @param path 指定路径
 * @throws FileNotFoundException 
   */
  public static void uploadFile(File file,String filename,String path) throws Exception{
	  File saveFile = new File(path+filename);
	  FileUtils.copyFile(file, saveFile);
  }

}
