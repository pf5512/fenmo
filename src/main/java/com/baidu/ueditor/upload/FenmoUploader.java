package com.baidu.ueditor.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.util.RequestUtil;

public class FenmoUploader {

	private HttpServletRequest request;
	private Map<String, Object> conf;
	private Logger logger = LoggerFactory.getLogger(FenmoUploader.class);
	
	public FenmoUploader(HttpServletRequest _request, Map<String, Object> _conf) {
		this.request = _request;
		this.conf = _conf;
	}

	public State doExec() {
		
		UserBean userBean = RequestUtil.getLoginUser(this.request);
		
		FileItemStream fileStream = null;
		
		boolean isAjaxUpload = this.request.getHeader( "Xrequested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(this.request)) {
			
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

    if ( isAjaxUpload ) {
        upload.setHeaderEncoding( "UTF-8" );
    }
    
    try{
    	
      FileItemIterator iterator = upload.getItemIterator(this.request);
	
			while (iterator.hasNext()) {
				
				fileStream = iterator.next();
	
				if (!fileStream.isFormField()){
					break;
				}
				fileStream = null;
			}
	
			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
		
			
			String userPhone = userBean != null ? String.valueOf(userBean.getPhone()) : String.valueOf(System.currentTimeMillis());
		
			//nignx路径
			String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
			
			File tempFile = new File(tempPath);
			if(!tempFile.exists() && !tempFile.isDirectory()){
				FileUtils.forceMkdir(tempFile);
			}
			
			//原文件名
	    String  fileName = fileStream.getName();
	    
	    //后缀
	    String  fileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
	    
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	    
	    String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000)+"." + fileExt;
	   
	    //此文件只能在linux下才能生成
	    File file = new File(tempPath,newFileName);
	    
	    InputStream is = fileStream.openStream();
	    
	    FileUtils.copyInputStreamToFile(fileStream.openStream(),file); 
	    
	    is.close();
	    
	    String tpUrl = NginxUtil.buildNginxUrl(this.request, userPhone, newFileName);
	    
	    BaseState storageState = new BaseState();
	    
	    storageState.putInfo("url", tpUrl);
	        
			storageState.putInfo("type", fileExt);
			
			storageState.putInfo("original", fileName);
			
			return storageState;
			
		} catch (FileUploadException e) {
			logger.error("----FenmoUploader----文件上传失败",e);
			e.printStackTrace();
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("----FenmoUploader----文件上传失败",e);
		}
    logger.error("----FenmoUploader----文件上传失败");
    return new BaseState(false, AppInfo.IO_ERROR);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, Object> getConf() {
		return conf;
	}

	public void setConf(Map<String, Object> conf) {
		this.conf = conf;
	}

	public static void main(String[] args) throws IOException {
		FileUtils.forceMkdir(new File("F:\\tomcat\\apache-tomcat-7.0.67-windows-x64\\apache-tomcat-7.0.67\\webapps\\fenmo\\uploadTmp"));
	}
}
