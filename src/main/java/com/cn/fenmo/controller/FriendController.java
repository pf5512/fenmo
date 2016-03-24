package com.cn.fenmo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cn.fenmo.pojo.Friend;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.service.FriendService;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.util.Md5Util;
import com.cn.fenmo.util.StringUtil;
import com.cn.fenmo.util.ToJson;
import com.cn.fenmo.util.UserCnst;
import com.cn.fenmo.util.ViewPage;
import com.easemob.server.httpclient.api.EasemobIMUsers;
import com.fasterxml.jackson.databind.node.ObjectNode;
@Controller
@RequestMapping("/friend")
public class FriendController extends ToJson{
   @Autowired
   private FriendService friendService;
   @Autowired
   private IUserService userService;
   /** ��Ӻ�������(����)*/
   @RequestMapping("/addFriend")
   public String addFriend(@RequestParam String userPhone,@RequestParam String friendPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     if(!StringUtil.isMobileNO(friendPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     if(userPhone.equals(friendPhone)){
       toExMsg(response,"��������Լ�Ϊ����");
       return null;
     }
     UserBean bean = this.userService.getUserBeanByUserPhone(friendPhone);
     if(bean==null){
       toExMsg(response,UserCnst.ADD_USER_NOT_EXIST);//��ӵ��û�������
       return null;
     }else{
       Friend friend = this.friendService.getFreind(userPhone, friendPhone);
       if(friend!=null){
         if(friend.getState()==UserCnst.REQUEST_SQ){
           toExMsg(response,"��Ҫ�ظ���������");
           return null;
         }else if(friend.getState()==UserCnst.REQUEST_TG){
           toExMsg(response,"�Ѿ�����ĺ���");
           return null;
         }else if(friend.getState()==UserCnst.REQUEST_JJ){
           toExMsg(response,"��������ѱ��Է��ܾ�");
           return null;
         }
       }else{
         Friend friendAdd =  new Friend();
         friendAdd.setUserphone(userPhone);
         friendAdd.setFriendphone(friendPhone);
         friendAdd.setState(UserCnst.REQUEST_SQ);
         if(this.friendService.save(friendAdd)){
           toExSuccMsg(response, "��������ɹ�");
           return null;
         }else {
           toExMsg(response,UserCnst.FRIENDER_ADD);
         }
       }
     }
     return null;
   }
   
   /** ͨ����������(����)*/
   @RequestMapping("/passFriend")
   public String passFriend(@RequestParam String userPhone,@RequestParam String friendPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     if(!StringUtil.isMobileNO(friendPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     UserBean bean = this.userService.getUserBeanByUserPhone(friendPhone);
     if(bean==null){
       toExMsg(response,UserCnst.ADD_USER_NOT_EXIST);//��ӵ��û�������
       return null;
     }else{
       Friend friend = this.friendService.getFreind(userPhone, friendPhone);
       if(friend!=null){
         ObjectNode objectNode = EasemobIMUsers.addFriendSingle(Md5Util.getMd5Value(userPhone),Md5Util.getMd5Value(friendPhone));
         if(objectNode==null){
           toExMsg(response,UserCnst.HX_FRIENDER_PASS);
           return null;
         }else{
           String statusCode = objectNode.get("statusCode").toString();
           if("200".equals(statusCode)){
             friend.setState(UserCnst.REQUEST_TG);
             if(!this.friendService.update(friend)){
               toExMsg(response,UserCnst.FRIENDER_PASS);
               return null;
             }else{
               toExSuccMsg(response,UserCnst.SUCCESS_PASS);
               return null;
             }
           }else{
             toExMsg(response,UserCnst.HX_FRIENDER_PASS);
             return null;
           }
         }
       }else{
         toExMsg(response,"���������¼������!");
       }
     }
     return null;
   }
   
   /**ɾ������*/
   @RequestMapping("/deleteFriend")
   public String deleteFriend(@RequestParam String userPhone,@RequestParam String friendPhone,HttpServletRequest request,HttpServletResponse response){
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     if(!StringUtil.isMobileNO(friendPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     UserBean bean = this.userService.getUserBeanByUserPhone(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.USER_NOT_EXIST);//�û�������
       return null;
     }else{
       ObjectNode objectNode = EasemobIMUsers.deleteFriendSingle(Md5Util.getMd5Value(userPhone), Md5Util.getMd5Value(friendPhone));
       if(objectNode==null){
         toExMsg(response,UserCnst.HX_FRIENDER_DELETE);
         return null;
       }else{
         String statusCode = objectNode.get("statusCode").toString();
         if("200".equals(statusCode)){
           if(!this.friendService.deleteFriend(userPhone,friendPhone)){
             toExMsg(response,UserCnst.FRIENDER_DELETE_FAIL);
             return null;
           }else{
             toExSuccMsg(response, UserCnst.FRIENDER_DELETE_SUCCESS);
             return null;
           }
         }else{
           toExMsg(response,UserCnst.HX_FRIENDER_DELETE);
         }
       }
     }
     return null;
   }
   
   /** �ܾ���������(����)*/
   @RequestMapping("/refuseFriend")
   public String refuseFriend(@RequestParam String userPhone,@RequestParam String friendPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     if(!StringUtil.isMobileNO(friendPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     UserBean bean = this.userService.getUserBeanByUserPhone(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.USER_NOT_EXIST);//�û�������
       return null;
     }else{
       Friend friend = this.friendService.getFreind(userPhone, friendPhone);
       if(friend!=null){
         friend.setState(UserCnst.REQUEST_JJ);
         if(!this.friendService.update(friend)){
           toExMsg(response,UserCnst.FRIENDER_REFUSE);
         }else{
           toExSuccMsg(response, "�ܾ���������ɹ�");
         }
       }else {
         toExMsg(response,"���������¼������!");
       }
     }
     return null;
   }
   
   /** ��ҳ��ȡ�Ѿ����㷢�ͼ�Ϊ����������û�*/
   @RequestMapping("/waitPassFriend")
   public String getWaitPassFriend(@RequestParam int state,@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     String start = request.getParameter("start");
     String limit = request.getParameter("limit");
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     UserBean bean = this.userService.getUserBeanByUserPhone(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.USER_NOT_EXIST);//�û�������
       return null;
     }else{
       Map<String, Object> parmars =  new HashMap<String, Object>();
       parmars.put("userPhone", userPhone);
       parmars.put("state", UserCnst.REQUEST_SQ);
       int count =  this.userService.selectMyFriendCount(parmars);
       List myfriendList = new ArrayList<Object>();
       if(count>0){
         ViewPage viewPage = new ViewPage();
         if(StringUtil.isNumeric(start)){
           parmars.put("start",start);
         }else{
           parmars.put("start", viewPage.getPageStart());
         }
         if(StringUtil.isNumeric(limit)){
           parmars.put("limit",limit);
         }else{
           parmars.put("limit",viewPage.getPageLimit());
         }
         myfriendList = this.userService.getMyFriend(parmars);
         viewPage.setTotalCount(count);
         viewPage.setListResult(myfriendList);
         toViewPage(response,viewPage);
       }else {
         toJson(response,myfriendList);
       }
     }
     return null;
   }
  
   
   /**state��ע�û����û��Ĺ�ϵ�����ݸ�״̬����ȡ�û��ĺ��ѣ��Ѿ�������������û�*/
   @RequestMapping("/getMyFriend")
   public String getMyFriend(@RequestParam int state,@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     String start = request.getParameter("start");
     String limit = request.getParameter("limit");
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     UserBean bean = this.userService.getUserBeanByPhone(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.USER_NOT_EXIST);//�û�������
       return null;
     }else{
       Map<String, Object> parmars =  new HashMap<String, Object>();
       parmars.put("userPhone", userPhone);
       parmars.put("state",state);
       int count =  this.userService.selectMyFriendCount(parmars);
       List myfriendList= null;
       if(count>0){
         ViewPage viewPage = new ViewPage();
         if(StringUtil.isNumeric(start)){
           parmars.put("start",start);
         }else{
           parmars.put("start", viewPage.getPageStart());
         }
         if(StringUtil.isNumeric(limit)){
           parmars.put("limit",limit);
         }else{
           parmars.put("limit",viewPage.getPageLimit());
         }
         myfriendList = this.userService.getMyFriend(parmars);
         viewPage.setTotalCount(count);
         viewPage.setListResult(myfriendList);
         toViewPage(response,viewPage);
       }else {
         toJson(response,myfriendList);
       }
     }
     return null;
   }
   
   /**ģ�������ҵĺ���*/
   @RequestMapping("/searchMyFriend")
   public String searchMyFriend(@RequestParam String userPhone,@RequestParam String searchKey,HttpServletRequest request,HttpServletResponse response) throws IOException{
     String start = request.getParameter("start");
     String limit = request.getParameter("limit");
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     UserBean bean = this.userService.getUserBeanByPhone(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.USER_NOT_EXIST);//�û�������
       return null;
     }else{
       Map<String, Object> parmars =  new HashMap<String, Object>();
       parmars.put("userPhone", userPhone);
       parmars.put("searchKey", searchKey);
       parmars.put("state", UserCnst.REQUEST_TG);
       int count =  this.userService.selectMyFriendCountBy(parmars);
       List myfriendList = null;
       if(count>0){
         ViewPage viewPage = new ViewPage();
         if(StringUtil.isNumeric(start)){
           parmars.put("start",start);
         }else{
           parmars.put("start", viewPage.getPageStart());
         }
         if(StringUtil.isNumeric(limit)){
           parmars.put("limit",limit);
         }else{
           parmars.put("limit", viewPage.getPageLimit());
         }
         myfriendList = this.userService.searchMyfriend(parmars);
         viewPage.setTotalCount(count);
         viewPage.setListResult(myfriendList);
         toViewPage(response,viewPage);
       }else {
         toJson(response,myfriendList);
       }
     }
     return null;
   }
}
