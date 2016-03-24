package com.cn.fenmo.controller;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cn.fenmo.pojo.Filelist;
import com.cn.fenmo.service.FilelistService;


@Controller
@RequestMapping("/fileserver")
public class FileUploadController {
  
  @Autowired
  private FilelistService filelistService;
  
  @RequestMapping("/upload"  )
  public String upload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
    String userid = request.getParameter("userid");
    //创建一个通用的多部分解析器
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    //判断 request 是否有文件上传,即多部分请求
    if(multipartResolver.isMultipart(request)){
      //转换成多部分request  
      MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
      //取得request中的所有文件名
      Iterator<String> iter = multiRequest.getFileNames();
      while(iter.hasNext()){
        //取得上传文件
        MultipartFile file = multiRequest.getFile(iter.next());
        if(file != null){
          //取得当前上传文件的文件名称
          String myFileName = file.getOriginalFilename();
          //如果名称不为空,说明该文件存在，否则说明该文件不存在
          if(myFileName!=null && myFileName.trim() !=""){
//            System.out.println(myFileName);
            //重命名上传后的文件名
            long fileid = System.currentTimeMillis();
            String fileName =String.valueOf(fileid) ;
            //定义上传路径
            String path = "D:"+File.separatorChar + userid ;
            File dis = new File(path);
            if(!dis.exists()){
              dis.mkdir();
            }
            path  += File.separator  + fileName;
            
            File localFile = new File(path);
            file.transferTo(localFile);
            
            Filelist fileinfo = new Filelist();
            fileinfo.setFileid(fileid);
            fileinfo.setFilelength(file.getSize());
            fileinfo.setFilename(myFileName);
            fileinfo.setUploadtime(new Date());
//            fileinfo.setFileserver(fileserver);//先用默认的
            this.filelistService.insert(fileinfo);
            
          }
        }
      }
      
    }
    return null;
  }
  
  @RequestMapping("/download")
  public String download(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
    String fileid = request.getParameter("fileid");
    String userid = request.getParameter("userid");
    
    Filelist fileinfo = this.filelistService.getFileInfo(Long.parseLong(fileid)) ;
    String path = fileinfo.getFileserver();
    path = "D:"+File.separator+userid+File.separator+fileid;
    String fileName = fileinfo.getFilename();
//    try {
//      new net.devin.util.file.DoFileDownload().doDownload(path, fileName, request, response);
//    } catch (ServletException e) {
//      e.printStackTrace();
//    }
    
    return null;
  }
}
