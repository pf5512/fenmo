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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.Dynamic;
import com.cn.fenmo.pojo.DynamicComment;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.DynamicCommentService;
import com.cn.fenmo.service.DynamicService;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.util.DynamicCnst;
import com.cn.fenmo.util.StringUtil;
import com.cn.fenmo.util.ToJson;
import com.cn.fenmo.util.UserCnst;
import com.cn.fenmo.util.ViewPage;
import com.easemob.server.httpclient.api.EasemobIMUsers;

@Controller
@RequestMapping("/dynamic")
public class DynamicController extends ToJson {
  private static final Logger LOGGER = LoggerFactory.getLogger(EasemobIMUsers.class);
  @Autowired
  private DynamicService dynamicService;
  @Autowired
  private DynamicCommentService dynamicCommentService;
  @Autowired
  private IUserService userService;

  /*��ȡ�ܱ�(�ɽ���Զ����)��̬,û�е�½Ҳ���Ի�ȡ��̬��Ϣ*/
  @RequestMapping("/getDtPage")
  public void getPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    params.put("state",DynamicCnst.PUBLISH);
    int count = this.dynamicService.selectDtCount(params);
    List list = null;
    if(count>0){
      viewPage.setTotalCount(count);
      list = this.dynamicService.getDtPageBy(params);
      viewPage.setListResult(list);
    }
    toViewPage(response,viewPage);
  }
  
  /*��ȡ�û��Լ������Ķ�̬��Ϣ*/
  @RequestMapping("/getDtPageByUserName")
  public void getDtPageByUserName(@RequestParam String userName,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
      params.put("limit",Integer.parseInt(limit));
    }else{
      params.put("limit", viewPage.getPageLimit());
    }
    params.put("userName", userName);
    params.put("state",DynamicCnst.PUBLISH);
    int count = this.dynamicService.selectCount(params);
    List list = null;
    if(count>0){
      viewPage.setTotalCount(count);
      list = this.dynamicService.getPageBy(params);
      viewPage.setListResult(list);
    }
    toViewPage(response,viewPage);
  }
  /*��ȡһ���Ѿ������Ķ�̬,û�е�½Ҳ���Ի�ȡ��̬��Ϣ���ѷ�����*/
  @RequestMapping("/getDynamicBean")
  public void getDynamicBean(@RequestParam long mainId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, Object> params =  new HashMap<String, Object>();
    params.put("mainId",mainId);
    params.put("state", DynamicCnst.PUBLISH);
    Dynamic dynamic  = this.dynamicService.getBeanBy(params);
    if(dynamic!=null){
      toJson(response, dynamic);
    }else {
      toExMsg(response,UserCnst.INFO_NO_EXIST);
    }
  }
  
  /*ֱ�ӷ���һ����̬*/
  @RequestMapping(value="/publishDynamic", method=RequestMethod.POST)  
  public String publishDynamic(@RequestParam String content,@RequestParam MultipartFile[] myfiles,@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{  
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
      return null;
    }
    Dynamic dynamic =  new Dynamic();
    dynamic.setMainid(new Date().getTime());
    dynamic.setContent(content);  
    dynamic.setUserName(userPhone);
    dynamic.setCreatedate(new Date());
    dynamic.setState(DynamicCnst.PUBLISH);
    dynamic.setZcount(0);
    for(MultipartFile myfile:myfiles){  
      if(myfiles.length!=1){
        toExMsg(response,"ֻ���ϴ�һ��ͼƬ");
        return null;
      }
      if(!myfile.isEmpty()){  
        String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
        String  fileName = myfile.getOriginalFilename();
        String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
        //���ļ�ֻ����linux�²�������
        File file = new File(tempPath,newFileName);
        FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
        String tpUrl= NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
        dynamic.setImgUrl(tpUrl);
      }
    }
    if(this.dynamicService.save(dynamic)){
      toExSuccMsg(response, UserCnst.SUCCESS_PUBLISH);
    }else {
      toExMsg(response,UserCnst.INFO_SAVE_FAIL);
    }
    return null;  
  } 
  
  /*���淢����̬ʱ�ϴ���ͼƬ*/
 @RequestMapping(value="/uploadDtImg", method=RequestMethod.POST)  
 public String uploadDtImg(@RequestParam String userPhone,@RequestParam MultipartFile[] myfiles, HttpServletRequest request,HttpServletResponse response) throws IOException{  
   UserBean bean= null;
   String token = (String) RedisClient.get(userPhone);
   if(StringUtil.isNotNull(token)){    
     bean = (UserBean)RedisClient.getObject(token);
   }
   if(token==null || bean==null){
     toExMsg(response,UserCnst.INFO_NO_LOGIN);
     return null;
   }
   List<String> urlList=new ArrayList<String>();
   for(MultipartFile myfile:myfiles){  
     if(!myfile.isEmpty()){  
       String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
       String  fileName = myfile.getOriginalFilename();
       String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
       SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
       String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
       //���ļ�ֻ����linux�²�������
       File file = new File(tempPath,newFileName);
       FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
       String tpUrl=NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
       urlList.add(tpUrl);
     }
   }
   toArrayJson(response,urlList);
   return null;  
 } 
  
  /*����һ����̬*/
  @RequestMapping(value="/saveDynamic", method=RequestMethod.POST)  
  public String saveDynamic(@RequestParam String content,@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{  
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
      return null;
    }
    Dynamic dynamic =  new Dynamic();
    dynamic.setContent(content);  
    dynamic.setUserName(userPhone);
    dynamic.setCreatedate(new Date());
    dynamic.setState(DynamicCnst.SAVE);
    dynamic.setZcount(0);
    if(this.dynamicService.save(dynamic)){
      toJson(response, dynamic);
    }else {
      toExMsg(response,UserCnst.INFO_SAVE_FAIL);
    }
    return null;  
  } 
  
  /*ɾ���Լ�����Ķ�̬,����ɾ����ص�����*/
  @RequestMapping("/deleteDynamic")
  public void deleteDynamic(@RequestParam String userPhone,@RequestParam long dynamicId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
    }else{
      Dynamic cbean =  this.dynamicService.getBeanById(dynamicId);
      if(cbean==null){
        toExMsg(response, "��¼������");
        return;
      }
      if(!cbean.getUserName().equals(userPhone)){
        toExMsg(response, "û��Ȩ��");
        return;
      }else{
        if(this.dynamicService.delete(dynamicId)){
          toExSuccMsg(response, "ɾ���ɹ�");
        }else {
          toExMsg(response, "ɾ��ʧ��");
        }
      }
    }
  }

  
  /*Ϊ��̬���ޣ�û�е�½Ҳ���Ե���*/
  @RequestMapping("/admire")
  public void admire(@RequestParam long dynamicId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(!this.dynamicService.updateZCount(dynamicId)){
      toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
    }else{
      toExSuccMsg(response, "лл����");
    }
  }
  
  
  /*��ҳ��ȡĳ����̬�µ�����,��Ҫ�����۵��û���Ϣ��ȡ��*/
  @RequestMapping("/getDyCommentPage")
  public void getDyCommentPage(@RequestParam long dynamicId,HttpServletRequest request, HttpServletResponse response) throws IOException {
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
      params.put("limit",Integer.parseInt(limit));
    }else{
      params.put("limit", viewPage.getPageLimit());
    }
    params.put("dynamicId", dynamicId);
    int count = this.dynamicCommentService.getDtComentCount(params);
    List list = null;
    if(count>0){
      viewPage.setTotalCount(count);
      list = this.dynamicCommentService.getDtComentPage(params);
      viewPage.setListResult(list);
    }
    toViewPage(response,viewPage);
  }
  
  /*�Զ�̬��������*/
  @RequestMapping("/discuss")
  public void discuss(@RequestParam String userPhone,@RequestParam String content,@RequestParam long dynamicId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
    }else{
      DynamicComment cbean =  new DynamicComment();
      cbean.setDynamicid(dynamicId);
      cbean.setContent(content);
      cbean.setUserName(userPhone);
      if(this.dynamicCommentService.save(cbean)){
        toExSuccMsg(response, "���۳ɹ�");
      }
    }
  }
  /*ɾ���Լ����������*/
  @RequestMapping("/deleteDtComment")
  public void deleteDtComment(@RequestParam String userPhone,@RequestParam long commentId,HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserBean bean= null;
    String token = (String) RedisClient.get(userPhone);
    if(StringUtil.isNotNull(token)){    
      bean = (UserBean)RedisClient.getObject(token);
    }
    if(token==null || bean==null){
      toExMsg(response,UserCnst.INFO_NO_LOGIN);
    }else{
      DynamicComment cbean =  this.dynamicCommentService.getBeanById(commentId);
      if(cbean!=null){
        toExMsg(response, "��¼������");
        return;
      }
      if(!cbean.getUserName().equals(userPhone)){
        toExMsg(response, "û��ɾ��Ȩ��");
        return;
      }else{
        if(this.dynamicCommentService.delete(commentId)){
          toExSuccMsg(response, "ɾ���ɹ�");
        }else {
          toExMsg(response, "ɾ��ʧ��");
        }
      }
    }
  }
}