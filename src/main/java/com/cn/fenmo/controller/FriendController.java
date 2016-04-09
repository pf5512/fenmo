package com.cn.fenmo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cn.fenmo.disruptor.BoatDisruptor;
import com.cn.fenmo.gt.client.PushClient;
import com.cn.fenmo.gt.push.PushObject;
import com.cn.fenmo.pojo.Friend;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.FriendService;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.test.ExceptionHandler;
import com.cn.fenmo.test.PushEventHandler;
import com.cn.fenmo.util.CNST;
import com.cn.fenmo.util.StringUtil;
import com.cn.fenmo.util.ToJson;
import com.cn.fenmo.util.UserCnst;
import com.cn.fenmo.util.ViewPage;
@Controller
@RequestMapping("/friend")
public class FriendController extends ToJson{
   public static  BoatDisruptor boatDisruptor = new BoatDisruptor();
   static{
      boatDisruptor.getDisruptor().handleEventsWith(new PushEventHandler());
      boatDisruptor.getDisruptor().handleExceptionsWith(new ExceptionHandler());
      boatDisruptor.getDisruptor().start();
   }
   @Autowired
   private FriendService friendService;
   @Autowired
   private IUserService userService;
   /** ��Ӻ�������(����)*/
   @RequestMapping("/addFriend")
   public String addFriend(@RequestParam String userPhone,@RequestParam String friendPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     UserBean userbean = getUserBeanFromRedis(userPhone);
     if(userbean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }
     if(userPhone.equals(friendPhone)){
       toExMsg(response,"��������Լ�Ϊ����");
       return null;
     }
     UserBean bean = this.userService.getUserBeanByPhone(friendPhone);
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
           PushObject object = new PushObject("��������",userPhone+"���㷢�ͺ�������",PushClient.PUSH_FRIEND_ADD,Arrays.asList(friendPhone),PushClient.PUSH_BY_ALIAS);
           boatDisruptor.ondata(object);
           toExSuccMsg(response, "success");
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
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       if(!StringUtil.isMobileNO(friendPhone)){
         toExMsg(response,UserCnst.PHONE_NOTCANUSER);
         return null;
       }
       Friend friend = this.friendService.getFreind(userPhone, friendPhone);
       if(friend!=null){
         friend.setState(UserCnst.REQUEST_TG);
         if(!this.friendService.update(friend)){
           toExMsg(response,UserCnst.FRIENDER_PASS);
           return null;
         }else{
           PushObject object = new PushObject("ͨ����������","�Է�ͨ������ĺ�������",PushClient.PUSH_FRIEND_AGREE,Arrays.asList(friendPhone),PushClient.PUSH_BY_ALIAS);
           boatDisruptor.ondata(object);
           toExSuccMsg(response, "success");
         }
       }else{
         toExMsg(response,"���Ѳ�����!");
       }
     }
     return null;
   }
   
   /**ɾ������*/
   @RequestMapping("/deleteFriend")
   public String deleteFriend(@RequestParam String userPhone,@RequestParam String friendPhone,HttpServletRequest request,HttpServletResponse response){
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       if(!StringUtil.isMobileNO(friendPhone)){
         toExMsg(response,UserCnst.PHONE_NOTCANUSER);
         return null;
       }
       if(!this.friendService.deleteFriend(userPhone,friendPhone)){
         toExMsg(response,UserCnst.FRIENDER_DELETE_FAIL);
         return null;
       }else{
         toExSuccMsg(response, UserCnst.FRIENDER_DELETE_SUCCESS);
         return null;
       }
     }
   }
   
   /** �ܾ���������(����)*/
   @RequestMapping("/refuseFriend")
   public String refuseFriend(@RequestParam String userPhone,@RequestParam String friendPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       if(!StringUtil.isMobileNO(friendPhone)){
         toExMsg(response,UserCnst.PHONE_NOTCANUSER);
         return null;
       }
       Friend friend = this.friendService.getFreind(userPhone, friendPhone);
       if(friend!=null){
         friend.setState(UserCnst.REQUEST_JJ);
         if(!this.friendService.update(friend)){
           toExMsg(response,UserCnst.FRIENDER_REFUSE);
         }else{
           PushObject object = new PushObject("�ܾ���������","�Է��ܾ�����ĺ�������",PushClient.PUSH_FRIEND_REJECT,Arrays.asList(friendPhone),PushClient.PUSH_BY_ALIAS);
           boatDisruptor.ondata(object);
           toExSuccMsg(response, "success");
         }
       }else {
         toExMsg(response,"���������¼������!");
       }
     }
     return null;
   }
   
   /** ��ҳ��ȡ�ȴ�ͨ������������û�������ҳ���ϡ��µ����ѡ�����¼����õĽӿڣ�*/
   @RequestMapping("/waitPassFriend")
   public String getWaitPassFriend(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       String start = request.getParameter("start");
       String limit = request.getParameter("limit");
       Map<String, Object> parmars =  new HashMap<String, Object>();
       parmars.put("userPhone", userPhone);
       parmars.put("state", UserCnst.REQUEST_SQ);
       int count =  this.userService.selectMyFriendCount(parmars);
       List myfriendList = new ArrayList<Object>();
       if(count>0){
         ViewPage viewPage = new ViewPage();
         if(StringUtil.isNumeric(start)){
           parmars.put("start",Integer.parseInt(start));
         }else{
           parmars.put("start", viewPage.getPageStart());
         }
         if(StringUtil.isNumeric(limit)){
           parmars.put("limit",Integer.parseInt(limit));
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
   @RequestMapping("/selectMyFriendRequest")
   public String selectMyFriendRequest(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response){
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       Map<String, Object> parmars =  new HashMap<String, Object>();
       parmars.put("userPhone", userPhone);
       parmars.put("state",1); 
       //������ѯ���������ʵ�֡�
       List<UserBean> myfriendList = new ArrayList<UserBean>();
       myfriendList = this.userService.selectMyFriendRequest(parmars);
       toArrayJson(response, myfriendList);
     }
     return null;
   }
   @RequestMapping("/getMyFriend")
   public String getMyFriend(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       String start = request.getParameter("start");
       String limit = request.getParameter("limit");
       Map<String, Object> parmars =  new HashMap<String, Object>();
       parmars.put("userPhone", userPhone);
       parmars.put("state",3);
       int count =  this.userService.selectMyFriendCount(parmars);
       List<UserBean> myfriendList= new ArrayList<UserBean>();
       ViewPage viewPage = new ViewPage();
       if(StringUtil.isNumeric(start)){
         parmars.put("start",Integer.parseInt(start));
       }else{
         parmars.put("start", viewPage.getPageStart());
       }
       if(StringUtil.isNumeric(limit)){
         parmars.put("limit",Integer.parseInt(limit));
       }else{
         parmars.put("limit",viewPage.getPageLimit());
       }
       myfriendList = this.userService.getMyFriend(parmars);
       viewPage.setTotalCount(count);
       viewPage.setListResult(myfriendList);
       toViewPage(response,viewPage);
     }
     return null;
   }
   
   /**ģ�������ҵĺ���*/
   @RequestMapping("/searchMyFriend")
   public String searchMyFriend(@RequestParam String userPhone,@RequestParam String searchKey,HttpServletRequest request,HttpServletResponse response) throws IOException{
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       String start = request.getParameter("start");
       String limit = request.getParameter("limit");
       Map<String, Object> parmars =  new HashMap<String, Object>();
       parmars.put("userPhone", userPhone);
       parmars.put("searchKey", searchKey);
       parmars.put("state", UserCnst.REQUEST_TG);
       int count =  this.userService.selectMyFriendCountBy(parmars);
       List<UserBean> myfriendList  =  new ArrayList<UserBean>();
       ViewPage viewPage = new ViewPage();
       if(StringUtil.isNumeric(start)){
         parmars.put("start",Integer.parseInt(start));
       }else{
         parmars.put("start", viewPage.getPageStart());
       }
       if(StringUtil.isNumeric(limit)){
         parmars.put("limit",Integer.parseInt(limit));
       }else{
         parmars.put("limit", viewPage.getPageLimit());
       }
       myfriendList = this.userService.searchMyfriend(parmars);
       viewPage.setTotalCount(count);
       viewPage.setListResult(myfriendList);
       toViewPage(response,viewPage);
     }
     return null;
   }
   private UserBean getUserBeanFromRedis(String userPhone){
     UserBean user =  this.userService.getUserBeanByPhone(userPhone);
     return user;
   }
}
