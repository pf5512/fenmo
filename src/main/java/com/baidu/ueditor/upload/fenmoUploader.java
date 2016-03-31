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

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.util.RequestUtil;

public class fenmoUploader {

	private HttpServletRequest request;
	private Map<String, Object> conf;
	
	public fenmoUploader(HttpServletRequest _request, Map<String, Object> _conf) {
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
	
				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}
	
			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
		
			
			String userPhone =userBean != null ? String.valueOf(userBean.getPhone()) : "15068164353";
			//nignx路径
			String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
			
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
	        
	        String tpUrl = RequestUtil.HTTPHEAD + NginxUtil.getNginxIP() + File.separatorChar + userPhone + File.separatorChar + newFileName;
	        
	        BaseState storageState = new BaseState();
	        
	        storageState.putInfo("url", tpUrl);
	        
			storageState.putInfo("type", fileExt);
			
			storageState.putInfo("original", fileName);
			
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {

		}
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

}
