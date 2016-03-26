package com.cn.fenmo.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.News;
import com.cn.fenmo.pojo.NewsComment;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.NewsCommentService;
import com.cn.fenmo.service.NewsService;
import com.cn.fenmo.util.CNST;
import com.cn.fenmo.util.NewsCnst;
import com.cn.fenmo.util.StringUtil;
import com.cn.fenmo.util.ToJson;
import com.cn.fenmo.util.UserCnst;
import com.cn.fenmo.util.ViewPage;

@Controller
@RequestMapping("/news")
public class NewsController extends ToJson {
  @Autowired
  private NewsService newsService;
  
  @Autowired
  private NewsCommentService newsCommentService;
  
  
  private final int HEAD_LIMIT=3;
  
  private final String HTTPHEAD="http://";
  
  //获取首页上显示的新闻(目前只显示最新的三条新闻) 
  @RequestMapping("/getNewsHeadPage")
  public void getNewsHeadPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String,Object> params = new HashMap<String,Object>();
    params.put("state",NewsCnst.PUBLISH);
    params.put("limit",HEAD_LIMIT);
    List<News> list = this.newsService.getNewsHeadPage(params);
    toArrayJson(response, list);
  }

  /*获取某人发布的新闻*/
  @RequestMapping("/getNewsPage")
  public void getPage(@RequestParam String userPhone,HttpServletRequest request, HttpServletResponse response) throws IOException {
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    Map<String,Object> params = new HashMap<String,Object>();
    ViewPage viewPage = new ViewPage();
    params.put("userName",userPhone);
    if(StringUtil.isNumeric(start)){
      params.put("start", Integer.parseInt(start));
    }else{
      params.put("start", viewPage.getPageStart());
    }
    if(StringUtil.isNumeric(limit)){
      params.put("limit", Integer.parseInt(limit));
    }else{
      params.put("limit", viewPage.getPageLimit());
    }
    params.put("state",NewsCnst.PUBLISH);
    int count = this.newsService.selectCount(params);
    List<News> list = null;
    if(count>0){
      list = this.newsService.selectBeanBy(params);
      for(int i=0;i<list.size();i++){
        News  news = list.get(i);
        String[] imgUrls = StringUtil.getImgStr(news.getContent()).split("\\$");
        if(!"".equals(imgUrls)){
          news.setNewHeadImgUrl(imgUrls[0]);
        }
      }
      viewPage.setTotalCount(count);
      viewPage.setListResult(list);
    } 
    toViewPage(response,viewPage);
  }
  
  /*获取一个新闻*/
  @RequestMapping("/getBean")
  public void getBean(@RequestParam long mainId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    News news  = this.newsService.getBeanById(mainId);
    if(news!=null){
      toJson(response, news);
    }else {
      toExMsg(response,UserCnst.INFO_NO_EXIST);
    }
  }
  /*保存发布新闻时上传的图片*/
  @RequestMapping(value="/uploadNewsImg", method=RequestMethod.POST)  
  public String uploadNewsImg(@RequestParam String userPhone,@RequestParam MultipartFile[] myfiles, HttpServletRequest request,HttpServletResponse response) throws IOException{  
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
      return null;
    }
    List<String> imgurlList=new ArrayList<String>();
    for(MultipartFile myfile:myfiles){  
      if(!myfile.isEmpty()){  
        String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
        String  fileName = myfile.getOriginalFilename();
        String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
        //此文件只能在linux下才能生成
        File file = new File(tempPath,newFileName);
        FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
        String tpUrl=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
        imgurlList.add(tpUrl);
      }
    }
    toArrayJson(response,imgurlList);
    return null;  
  }
  /*保存新闻*/
  @RequestMapping("/save")
  public void save(@RequestParam String userPhone,@RequestParam String content,HttpServletRequest request, HttpServletResponse response) throws IOException {
    News bean =  new News();
    bean.setUserName(userPhone);
    bean.setContent(content);
    bean.setState(NewsCnst.SAVE);
    bean.setCreatedate(new Date());
    bean.setNewsrc(CNST.CIRCLE_ZMT_STR);
    if(this.newsService.save(bean)){
      toJson(response,bean);
    }else{
      toExMsg(response,UserCnst.INFO_SAVE_FAIL);
    }
  }
  /*直接发表新闻,手机客服端发布的新闻都认为是自媒体新闻，娱乐，财经，房产等新闻由后台发布*/
  @RequestMapping("/publishNews")
  public String publishNews(@RequestParam String title,@RequestParam String content,@RequestParam String userPhone,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean= null; 
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
      return null;
    }else {
      News news = new News();
      news.setMainid(new Date().getTime());
      news.setTitle(title);
      news.setUserName(userPhone);
      news.setContent(content);
      news.setState(NewsCnst.PUBLISH);
      news.setCreatedate(new Date());
      news.setNewsrc(CNST.CIRCLE_ZMT_STR);
      news.setZcount(0);
      news.setNewstype(CNST.CIRCLE_ZMT);
      if(!this.newsService.save(news)){
        toExMsg(response,UserCnst.INFO_SAVE_FAIL);
        return null;
      }else{
        toExSuccMsg(response, UserCnst.SUCCESS_PUBLISH);
      }
    }
    return null;
  }
  
  /*为新闻点赞，没有登陆也可以点赞*/
  @RequestMapping("/admire")
  public void admire(@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(!this.newsService.updateZCount(newsId)){
      toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
    }else{
      toExSuccMsg(response, "谢谢点赞");
    }
  }
  
  /*删除自己发表的新闻,级联删除相关的评论*/
  @RequestMapping("/deleteNews")
  public void deleteNews(@RequestParam String userPhone,@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
    }else{
      News cbean =  this.newsService.getBeanById(newsId);
      if(cbean==null){
        toExMsg(response, "记录不存在");
        return;
      }
      if(!cbean.getUserName().equals(userPhone)){
        toExMsg(response, "没有权限");
        return;
      }else{
        if(this.newsService.delete(newsId)){
          toExSuccMsg(response, "删除成功");
        }else {
          toExMsg(response, "删除失败");
        }
      }
    }
  }
  
  /*用户为新闻进行评论,必须登陆*/
  @RequestMapping("/discuss")
  public void discuss(@RequestParam String userPhone,@RequestParam String content,@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
    }else{
      NewsComment cbean =  new NewsComment();
      cbean.setMainid(new Date().getTime());
      cbean.setNewsid(newsId);
      cbean.setContent(content);
      cbean.setUserName(userPhone);
      cbean.setCreatedate(new  Date());
      if(this.newsCommentService.save(cbean)){
        toExSuccMsg(response, "评论成功");
      }else{
        toExMsg(response, "评论失败");
      }
    }
  }
  
  /*删除自己发表的新闻评论*/
  @RequestMapping("/deleteNewsComment")
  public void deleteNewsComment(@RequestParam String userPhone,@RequestParam long commentId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
    }else{
      NewsComment cbean =  this.newsCommentService.getBeanById(commentId);
      if(cbean==null){
        toExMsg(response, "记录不存在");
        return;
      }
      if(!cbean.getUserName().equals(userPhone)){
        toExMsg(response, "没有权限");
        return;
      }else{
        if(this.newsCommentService.delete(commentId)){
          toExSuccMsg(response, "删除评论成功");
        }else {
          toExMsg(response, "删除评论失败");
        }
      }
    }
  }
  /*分页获取某条新闻下的评论，需要获取评论者的相关信息，如头像*/
  @RequestMapping("/getNewsCommentPage")
  public void getNewsCommentPage(@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    Map<String,Object> params = new HashMap<String,Object>();
    ViewPage viewPage = new ViewPage();
    if(StringUtil.isNumeric(start)){
      params.put("start", Integer.parseInt(start));
    }else{
      params.put("start", viewPage.getPageStart());
    }
    if(StringUtil.isNumeric(limit)){
      params.put("limit", Integer.parseInt(limit));
    }else{
      params.put("limit", viewPage.getPageLimit());
    }
    params.put("newsId", newsId);
    int count = this.newsCommentService.getNewsComentCount(params);
    List list = null;
    if(count>0){
      viewPage.setTotalCount(count);
      list = this.newsCommentService.getNewsComentPage(params);
      viewPage.setListResult(list);
    }
    toViewPage(response,viewPage);
  }
  
  /*为新闻评论点赞，没有登陆也可以点赞*/
  @RequestMapping("/admireComent")
  public void admireComent(@RequestParam long mainid,HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(!this.newsCommentService.updateZcount(mainid)){
      toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
    }else{
      toExSuccMsg(response, "success");
    }
  }
  
}