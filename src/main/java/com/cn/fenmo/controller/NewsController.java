package com.cn.fenmo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sun.misc.BASE64Decoder;

import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.News;
import com.cn.fenmo.pojo.NewsComment;
import com.cn.fenmo.pojo.NewsCommentUser;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.service.NewsCommentService;
import com.cn.fenmo.service.NewsService;
import com.cn.fenmo.util.CNST;
import com.cn.fenmo.util.NewsCnst;
import com.cn.fenmo.util.RequestUtil;
import com.cn.fenmo.util.StringUtil;
import com.cn.fenmo.util.ToJson;
import com.cn.fenmo.util.UserCnst;
import com.cn.fenmo.util.ViewPage;
import com.google.gson.Gson;

@Controller
@RequestMapping("/news")
public class NewsController extends ToJson {
  private Logger logger = LoggerFactory.getLogger(NewsController.class);
  private Gson gson=new Gson();
  @Autowired
  private NewsService newsService;
  
  @Autowired
  private IUserService userService;
  
  @Autowired
  private NewsCommentService newsCommentService;
  
  private final int HEAD_LIMIT=3;
  
  
  //��ȡ��ҳ����ʾ������(Ŀǰֻ��ʾ���µ���������) 
  @RequestMapping("/getNewsHeadPage")
  public void getNewsHeadPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String,Object> params = new HashMap<String,Object>();
    params.put("state",NewsCnst.PUBLISH);
    params.put("limit",HEAD_LIMIT);
    List<News> list = this.newsService.getNewsHeadPage(params);
    toArrayJson(response, list);
  }

  @RequestMapping(value = "uploadimage",method = RequestMethod.POST)
  public  void uplaodImg(HttpServletRequest req,HttpServletResponse resp){ 
    try {
      String imgCode = req.getParameter("filed");
      if (imgCode==null){
          return;
      }
      String imgArg[]=imgCode.split(",");
      if (imgArg == null||imgArg.length!=2||imgArg[1]==null){
          return; //ͼ������Ϊ��
      }
      String filePath =""; 
      Properties props=System.getProperties(); //���ϵͳ���Լ�    
      String osName = props.getProperty("os.name"); //����ϵͳ����    
      if(osName.indexOf("win")>0){
       filePath = req.getServletContext().getRealPath("/")+"news/";
      }else{
       filePath = NginxUtil.getNginxDisk() + File.separatorChar+"news/";
      }
      if (!new File(filePath).exists()){
        new File(filePath).mkdirs();
      }
      String name=System.currentTimeMillis()+".png";
      String imgFilePath = filePath+name;//�����ɵ�ͼƬ
      BASE64Decoder decoder = new BASE64Decoder();
      byte[] decodedBytes = decoder.decodeBuffer(imgArg[1]);
      BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
      if (image == null) {
          return;
      }
      File f = new File(imgFilePath);
      ImageIO.write(image, "png", f);
      HashMap hashMap=new HashMap();
      if(osName.indexOf("win")>0){
        hashMap.put("path","http://localhost:8088"+req.getContextPath()+"/news/"+name);
       }else{
        hashMap.put("path","http://60.190.243.154:80/news/"+name);
       }
      resp.getOutputStream().print(gson.toJson(hashMap));
      } catch (Exception e) {
          e.printStackTrace();
      }  
  }
  /*��ȡĳ�˷���������*/
  @RequestMapping("/getNewsPage")
  public void getNewsPage(@RequestParam String userPhone,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
  /*��ȡ�������(3��)������newType���*/
  @RequestMapping("/getInterfixNews")
  public void getInterfixNews(@RequestParam String newsType,HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String,Object> params = new HashMap<String,Object>();
    params.put("newsType",Integer.parseInt(newsType));
    params.put("state",NewsCnst.PUBLISH);
    params.put("start",0);
    params.put("limit",3);
    List<News> list = this.newsService.getInterfixNews(params);
    toArrayJson(response, list);
  }
  
  /*��ȡһ������*/
  @RequestMapping("/getBean")
  public void getBean(@RequestParam long mainId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    News news  = this.newsService.getBeanById(mainId);
    if(news!=null){
      toJson(response, news);
    }else {
      toExMsg(response,UserCnst.INFO_NO_EXIST);
    }
  }
  
  
  /*��������*/
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
  /*ֱ�ӷ�������,�ֻ��ͷ��˷��������Ŷ���Ϊ����ý�����ţ����֣��ƾ��������������ɺ�̨����*/
  @RequestMapping("/publishNews")
  public String publishNews(@RequestParam String title,@RequestParam String content,@RequestParam String userPhone,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
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
  
  /*Ϊ���ŵ��ޣ�û�е�½Ҳ���Ե���*/
  @RequestMapping("/admire")
  public void admire(@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(!this.newsService.updateZCount(newsId)){
      toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
    }else{
      toExSuccMsg(response, "лл����");
    }
  }
  
  /*ɾ���Լ����������,����ɾ����ص�����*/
  @RequestMapping("/deleteNews")
  public void deleteNews(@RequestParam String userPhone,@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return;
    }else{
      News cbean =  this.newsService.getBeanById(newsId);
      if(cbean==null){
        toExMsg(response, "��¼������");
        return;
      }
      if(!cbean.getUserName().equals(userPhone)){
        toExMsg(response, "û��Ȩ��");
        return;
      }else{
        if(this.newsService.delete(newsId)){
          toExSuccMsg(response, "ɾ���ɹ�");
        }else {
          toExMsg(response, "ɾ��ʧ��");
        }
      }
    }
  }
  
  /*�û�Ϊ���Ž�������,�����½*/
  @RequestMapping("/discuss")
  public void discuss(@RequestParam String userPhone,@RequestParam String content,@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return;
    }else{
      NewsComment cbean =  new NewsComment();
      cbean.setMainid(new Date().getTime());
      cbean.setNewsid(newsId);
      cbean.setContent(content);
      cbean.setUserName(userPhone);
      cbean.setCreatedate(new  Date());
      if(this.newsCommentService.save(cbean)){
        toExSuccMsg(response, "���۳ɹ�");
      }else{
        toExMsg(response, "����ʧ��");
      }
    }
  }
  
  /*ɾ���Լ��������������*/
  @RequestMapping("/deleteNewsComment")
  public void deleteNewsComment(@RequestParam String userPhone,@RequestParam long commentId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return;
    }else{
      NewsComment cbean =  this.newsCommentService.getBeanById(commentId);
      if(cbean==null){
        toExMsg(response, "��¼������");
        return;
      }
      if(!cbean.getUserName().equals(userPhone)){
        toExMsg(response, "û��Ȩ��");
        return;
      }else{
        if(this.newsCommentService.delete(commentId)){
          toExSuccMsg(response, "ɾ�����۳ɹ�");
        }else {
          toExMsg(response, "ɾ������ʧ��");
        }
      }
    }
  }
  /*��ҳ��ȡĳ�������µ����ۣ���Ҫ��ȡ�����ߵ������Ϣ����ͷ��*/
  @RequestMapping("/getNewsCommentPage")
  public void getNewsCommentPage(@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    Map<String,Object> params = new HashMap<String,Object>();
    ViewPage viewPage = new ViewPage();
    if(StringUtil.isNumeric(start)){
      params.put("start", Integer.parseInt(start));
      viewPage.setPageStart(Integer.parseInt(start));
    }else{
      params.put("start", viewPage.getPageStart());
    }
    if(StringUtil.isNumeric(limit)){
      params.put("limit", Integer.parseInt(limit));
      viewPage.setPageLimit(Integer.parseInt(limit));
    }else{
      params.put("limit", viewPage.getPageLimit());
    }
    params.put("newsId", newsId);
    params.put("field", "createDate");
    int count = this.newsCommentService.getNewsComentCount(params);
    List<NewsCommentUser> list = new ArrayList<NewsCommentUser>();
    if(count>0){
      viewPage.setTotalCount(count);
      list = this.newsCommentService.getNewsComentPage(params);
      viewPage.setListResult(list);
    }
    toViewPage(response,viewPage);
  }
  
  /*��ҳ��ȡĳ�������µ���������(����������������)����Ҫ��ȡ�����ߵ������Ϣ����ͷ��*/
  @RequestMapping("/getHotNewsCommentPage")
  public void getHotNewsCommentPage(@RequestParam long newsId,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    params.put("field", "zcount");
    int count = this.newsCommentService.getNewsComentCount(params);
    List<NewsCommentUser> list = new ArrayList<NewsCommentUser>();
    if(count>0){
      viewPage.setTotalCount(count);
      list = this.newsCommentService.getNewsComentPage(params);
      viewPage.setListResult(list);
    }
    toViewPage(response,viewPage);
  }
  
  public  UserBean getUserBeanFromRedis(String userPhone) {
    UserBean user = this.userService.getUserBeanByPhone(userPhone);
    return user;
  }
  
  /*Ϊ�������۵��ޣ�û�е�½Ҳ���Ե���*/
  @RequestMapping("/admireComent")
  public void admireComent(@RequestParam long mainid,HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println("�յ�����admireComent");
    if(!this.newsCommentService.updateZcount(mainid)){
      toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
    }else{
      toExSuccMsg(response, "success");
    }
  }

 /**
   * 
   * @Description: ��������
   * @author weiwj  
   * @date 2016-4-9 ����11:02:25
   */
  @RequestMapping("/addNews")
  public void addNews(HttpServletRequest request, HttpServletResponse response){
    boolean success = false;
    try {
      News news = RequestUtil.getBean(request, News.class);
      success = this.newsService.save(news);
    } catch (Exception e) {
      logger.error("----addNews---����News�������",e);
      e.printStackTrace();
    }
    toExSuccMsg(response, String.valueOf(success));
  }
  
  /**
   * 
   * @Description: ����id��ȡ��������
   * @author weiwj  
   * @date 2016-4-9 ����11:24:27  
   * @param mainid ��������
   * @param request
   * @param response
   */
  @RequestMapping("/getNewsById")
  public void getNewsById(@RequestParam long mainId, HttpServletRequest request, HttpServletResponse response){
    News news = this.newsService.selectByPrimaryKey(mainId);
    toJSON(response, news);
  }
  
  
  /**
   * 
   * @Description: ������������
   * @author weiwj  
   * @date 2016-4-9 ����5:14:26 
   * @param request
   * @param response
   */
  @RequestMapping("/updateNews")
  public void updateNews(HttpServletRequest request, HttpServletResponse response){
    boolean success = false;
    try {
      News news = RequestUtil.getBean(request, News.class);
      success = this.newsService.updateNews(news);
    } catch (Exception e) {
      logger.error("----updateNews---����News�������",e);
      e.printStackTrace();
    }
    toExSuccMsg(response, String.valueOf(success));
  }
  /**
   * 
   * @description ��ȡ�����б�
   * @author weiwj
   * @date ����7:51:23
   * @param request
   * @param response
   */
  @RequestMapping("/getNewsList")
  public void getNewsList(HttpServletRequest request,HttpServletResponse response){
    Map<String,Object> params = RequestUtil.getRequestParamMap(request);
    List<News> news = this.newsService.selectBeanBy(params);
    int total = this.newsService.selectCount(params);
    toViewPageForWeb(response, news, total);
  }
}
