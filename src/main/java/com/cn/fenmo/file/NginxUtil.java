package com.cn.fenmo.file;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.fenmo.util.PathUtil;
import com.cn.fenmo.util.RequestUtil;


public class NginxUtil {
	
  private static Logger logger = LoggerFactory.getLogger(NginxUtil.class);
  
  private static String UPLOADDIR = "uploadTmp";
  
  public static String getNginxDisk(){
  	
  	Properties prop = System.getProperties();
  	
  	String osName = prop.getProperty("os.name");
  	
  	if(osName.indexOf("Windows")>=0){
  			try {
  				//暂时在根目录下建立临时文件夹，不在web-inf下建立临时的图片上传目录，因为没有文件服务器，图片将会访问不到。
					String path = PathUtil.combineFilePath(PathUtil.getRootPath(),UPLOADDIR);
					return path; 
				} catch (Exception e) {
					logger.error("----getNginxDisk----获取图片上传路径出错",e);
					e.printStackTrace();
				}
  			return "";
  	}else{
  			ParseXML pasexml = new ParseXML();
  	    pasexml.parse("nginx.xml");
  	    Properties properties = pasexml.getProps();
  	    return properties.getProperty("nginxdisk");
  	}
  }
  
  
  public static String getNginxIP(){
    ParseXML pasexml = new ParseXML();
    pasexml.parse("nginx.xml");
    Properties properties = pasexml.getProps();
    String ip = properties.getProperty("ip");
    return ip;
  }
  

  /**
   * 
   * @description 生成图片的访问url
   * @author weiwj
   * @date 下午11:01:41
   * @return
   */
  public static String buildNginxUrl(HttpServletRequest request,String userPhone,String fileName){
  	Properties prop = System.getProperties();
  	String osName = prop.getProperty("os.name");
  	if(osName.indexOf("Windows")>=0){
  		//相当于 http://ip:port/fenmo
  		String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()
      + request.getServletContext().getContextPath()+ "/" + UPLOADDIR + "/" + userPhone+"/"+fileName;
  		return url;
  	}else{
  		String url = RequestUtil.HTTPHEAD + NginxUtil.getNginxIP()+ "/" + File.separatorChar + userPhone +  "/"  + fileName;
  		return url;
  	}
  }
}
