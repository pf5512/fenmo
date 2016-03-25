package com.cn.fenmo.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.Room;
import com.cn.fenmo.pojo.RoomUsers;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.IRoomService;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.service.RoomUsersService;
import com.cn.fenmo.util.Md5Util;
import com.cn.fenmo.util.RoomCnst;
import com.cn.fenmo.util.StringUtil;
import com.cn.fenmo.util.ToJson;
import com.cn.fenmo.util.UserCnst;
import com.cn.fenmo.util.ViewPage;
import com.easemob.server.httpclient.api.EasemobChatGroups;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("/room")
public class RoomController extends ToJson {
  @Autowired
  private IRoomService roomService;
  @Autowired
  private IUserService userService;
  @Autowired
  private RoomUsersService roomUsersService;
  
  private final String HTTPHEAD="http://";
  /**
   * ��ȡȺ����ϸ��Ϣ��û�е�½Ҳ���Ի�ȡȺ����Ϣ
   * @param request
   * @param response
   * @throws IOException
   */
  @RequestMapping("/getRoomById")
  public String getRoomById(@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room!=null){
      toJson(response, room);
    }else{
      toExMsg(response, "Ⱥ�鲻����");
    }
    return null;
  }

  /**
   * ����Ⱥ����������������Ⱥ�飬�����½
   * ����Ⱥ������(���֣��ƾ������ز�����ý��)��ȡȺ��,�����½
   * �˴�ֻ���ҹ���Ⱥ
   */
  @RequestMapping("/searchPageRoomsBy")
  public String searchPageRoomsBy(HttpServletRequest request,HttpServletResponse response) throws IOException {
    String roomName = request.getParameter("roomName");
    String type = request.getParameter("type");
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    ViewPage viewPage = new ViewPage();
    Map<String, Object> params = new HashMap<String, Object>();
    if(!StringUtil.isNumeric(start)){
      params.put("start", viewPage.getPageStart());
    }else{
      params.put("start", Integer.valueOf(start));
      viewPage.setPageStart(Integer.valueOf(start));
    }
    if(!StringUtil.isNumeric(limit)){
      params.put("limit", viewPage.getPageLimit());
    }else{
      params.put("limit", Integer.valueOf(limit));
      viewPage.setPageLimit(Integer.valueOf(limit));
    }
    if(StringUtil.isNotNull(roomName)){
      params.put("roomName",roomName);
    }
    if(StringUtil.isNumeric(type)){
      params.put("type",type);
    }
    params.put("ispublic",RoomCnst.ROOM_PUBLIC);
    List<Room> roomlist = null;
    int count = this.roomService.selectCount(params);
    if(count>0){
      roomlist =  (List<Room>) this.roomService.getRooms(params);
      viewPage.setTotalCount(count);
      viewPage.setListResult(roomlist);
    }
    toViewPage(response, viewPage);
    return null;
  }
  
  /**
   * ��ȡ�û������Ⱥ�飨�����û�userPhone��ȡ��
   * �˴����ҹ�����˽��Ⱥ
   */
  @RequestMapping("/getPageRoomsByUserPhone")
  public String searchPageRoomsByUserName(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    ViewPage viewPage = new ViewPage();
    Map<String, Object> params = new HashMap<String, Object>();
    if(!StringUtil.isNumeric(start)){
      params.put("start", viewPage.getPageStart());
    }else{
      params.put("start", Integer.valueOf(start));
      viewPage.setPageStart(Integer.valueOf(start));
    }
    if(!StringUtil.isNumeric(limit)){
      params.put("limit", viewPage.getPageLimit());
    }else{
      params.put("limit", Integer.valueOf(limit));
      viewPage.setPageLimit(Integer.valueOf(limit));
    }
    if(StringUtil.isNotNull(userPhone)){
      params.put("roomName",userPhone);
    }
    List<Room> roomlist = null;
    int count = this.roomService.selectCount(params);
    if(count>0){
      roomlist =  (List<Room>) this.roomService.getRooms(params);
      viewPage.setTotalCount(count);
      viewPage.setListResult(roomlist);
    }
    toViewPage(response, viewPage);
    return null;
  }
  
  /**
   * ��ȡ��ʾ����ҳ���ϵ�����Ⱥ��(Ĭ����ʾ����Ա�û�������Ⱥ�飬���ݹ���ԱuserName����)
   * �˴�ֻ���ҹ���Ⱥ
   */
  @RequestMapping("/getPageHotRooms")
  public String getPageHotRooms(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException {
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    ViewPage viewPage = new ViewPage();
    Map<String, Object> params = new HashMap<String, Object>();
    if(!StringUtil.isNumeric(start)){
      params.put("start", viewPage.getPageStart());
    }else{
      params.put("start", Integer.valueOf(start));
      viewPage.setPageStart(Integer.valueOf(start));
    }
    if(!StringUtil.isNumeric(limit)){
      params.put("limit", viewPage.getPageLimit());
    }else{
      params.put("limit", Integer.valueOf(limit));
      viewPage.setPageLimit(Integer.valueOf(limit));
    }
    if(StringUtil.isNotNull(userPhone)){
      params.put("roomName",userPhone);
    }
    List<Room> roomlist = null;
    params.put("ispublic",RoomCnst.ROOM_PUBLIC);
    int count = this.roomService.selectCount(params);
    if(count>0){
      roomlist =  (List<Room>) this.roomService.getRooms(params);
      viewPage.setTotalCount(count);
      viewPage.setListResult(roomlist);
    }
    toViewPage(response, viewPage);
    return null;
  }
  /**
   * �½�Ⱥ(��������Ⱥ����洴��),ֻ�е�½�����½�,�˴�������Ⱥ�ǹ���Ⱥ����Ⱥ�����ƿ���������,
   * @param request
   * @param response
   */
  @RequestMapping("/createPublicRoom")
  public String createPublicRoom(@RequestParam String userPhone,@RequestParam int type,@RequestParam String roomName,@RequestParam String desc,@RequestParam String subject,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    //�˷���ֻ���ҹ���Ⱥ
    List<Room> roomList = this.roomService.getRoomByName(roomName);
    for(int i=0;roomList!=null&&i<roomList.size();i++){
      Room room = roomList.get(i);
      if(room.getUserName().equals(userPhone)){
        toExMsg(response, "���Ѿ�������һ����"+roomName+"��Ⱥ");
        return null;
      }
    }
    ObjectNode datanode = JsonNodeFactory.instance.objectNode();
    datanode.put("groupname",roomName);
    datanode.put("desc",desc);
    // Ⱥ�����ͣ� true ����Ⱥ��false ˽��Ⱥ,
    datanode.put("public",true);
    datanode.put("maxusers",300);
    // ������Ϊ��ѡ�ģ���ֵΪfalseʱ����Ⱥ����ҪȺ����׼,Ϊtrueʱ��ҪȺ������
    datanode.put("approval",false);
    // true ����Ⱥ��Ա�����˼����Ⱥ;
    datanode.put("allowinvites", true);
    //�Ƿ�ֻ��Ⱥ��Ա���Խ������ԣ�true �� �� false ��
    datanode.put("membersonly", false );
    datanode.put("owner",Md5Util.getMd5Value(userPhone));
    ObjectNode node = EasemobChatGroups.creatChatGroups(datanode);
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// ����½��ɹ�
        JsonNode data = node.get("data");
        String groupId = data.get("groupid").asText();
        Room bean = new Room();
        bean.setGroupId(groupId);
        bean.setUserName(userPhone);
        bean.setRoomName(roomName);
        bean.setDescription(desc);
        bean.setSubject(subject);
        bean.setType(type);
        bean.setIspublic(RoomCnst.ROOM_PUBLIC);
        bean.setUserCounts(1);
        bean.setCreatedate(new Date());
        if(this.roomService.save(bean)){
          toJson(response, bean);
        }
      }else if("400".equals(statusCode)){
        String error_description = node.get("error_description").toString();
        toExMsg(response, error_description);
      }
    }
    return null;
  }
  
  
  /**
   * �½�Ⱥ(˽��Ⱥ������Ⱥ��ʱ������Ⱥ),ֻ�е�½�����½�,��Ⱥ�����Ʋ�����������,���ǻ�ȡ�ҵ�Ⱥ����Ի�ȡ��
   * @param request
   * @param response
   */
  @RequestMapping("/createPrivateRoom")
  public String createPrivateRoom(@RequestParam String userPhone,@RequestParam String members,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    ObjectNode datanode = JsonNodeFactory.instance.objectNode();
    datanode.put("groupname","Ⱥ��");
    datanode.put("desc","����");
    // Ⱥ�����ͣ� true ����Ⱥ��false ˽��Ⱥ,
    datanode.put("public",false);
    datanode.put("maxusers",100);
    // ������Ϊ��ѡ�ģ���ֵΪfalseʱ����Ⱥ����ҪȺ����׼,Ϊtrueʱ��ҪȺ������
    datanode.put("approval",true);
    // true ����Ⱥ��Ա�����˼����Ⱥ;
    datanode.put("allowinvites",true);
    //�Ƿ�ֻ��Ⱥ��Ա���Խ������ԣ�true �� �� false ��
    datanode.put("membersonly",true);
    datanode.put("owner",Md5Util.getMd5Value(userPhone));
    // Ⱥ���Աmembers��  Ⱥ���ԱΪ��$���ŷָ���username�ַ���
    String[] array = null;
    List<UserBean> userList = null;
    if(StringUtil.isNotNull(members)) {
      array = members.split("\\$");
      userList = this.userService.getUserListByUserPhoneList(Arrays.asList(array));
      if(userList==null||userList.size()==0||userList.size()!=array.length){
        toExMsg(response, UserCnst.USER_NOT_EXIST);
        return null;
      }
      ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
      String[] temp = new String[userList.size()];
      for(int i=0;i<userList.size();i++){
        temp[i] = Md5Util.getMd5Value(userList.get(i).getUsername());
        arrayNode.add(temp[i]);
      }
      datanode.put("members", arrayNode);
      if (array!= null && array.length > 0) {
        datanode.put("affiliations",Arrays.toString(temp));
        datanode.put("affiliations_count",temp.length);
      }
    }
    ObjectNode node = EasemobChatGroups.creatChatGroups(datanode);
    if (node!= null) {
      String statusCode = node.get("statusCode").toString();
      if("200".equals(statusCode)) {// ����½��ɹ�
        JsonNode data = node.get("data");
        String groupId = data.get("groupid").asText();
        Room bean = new Room();
        bean.setGroupId(groupId);
        bean.setUserName(userPhone);
        bean.setRoomName("Ⱥ��");
        bean.setIspublic(RoomCnst.ROOM_PRIVATE);
        bean.setCreatedate(new Date());
        bean.setUserCounts(array.length+1);
        if(this.roomService.save(bean)){
          //Ⱥ�鴴���ɹ�֮������Ⱥ���Ա���в��������Ա���˴�����������
          List<RoomUsers> list = new ArrayList<RoomUsers>();
          for(int i = 0; i < array.length; i++) {
            RoomUsers roomUsers = new RoomUsers();
            roomUsers.setMainid((long)userList.get(i).getMainid()+new Date().getTime());
            roomUsers.setGroupId(groupId);
            roomUsers.setUserName(array[i]);
            roomUsers.setStartdate(new Date());
            roomUsers.setUserRemark(userList.get(i).getNickname());
            list.add(roomUsers);
          }
          if(this.roomUsersService.addBatchRecord(list)){
            toJson(response, bean);
          }
        }
      }else if("400".equals(statusCode)){
        String error_description = node.get("error_description").toString();
        toExMsg(response, error_description);
      }
    }
    return null;
  }

  /**
   * �޸�Ⱥ������,�޸�Ⱥ���roomNameֻ��Ⱥ�������޸�,�˴��޸ĵ�Ⱥ��Ϊ����Ⱥ
   * @param request
   * @param response
   */
  @RequestMapping("/updateRoomName")
  public String updateRoomName(@RequestParam String userPhone,@RequestParam String groupId,@RequestParam String roomName,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room bean = this.roomService.getRoomByGroupId(groupId);
    if(bean!=null && !bean.getUserName().equals(userPhone)){
      toExMsg(response, "û���޸�Ȩ��");
      return null;
    }
    bean.setRoomName(roomName);
    ObjectNode datanode = JsonNodeFactory.instance.objectNode();
    datanode.put("groupname",roomName);
    datanode.put("description", bean.getDescription());
    ObjectNode node = EasemobChatGroups.updateChatGroups(datanode, groupId);
    if(node != null) {
      String statusCode = node.get("statusCode").toString();
      if("200".equals(statusCode) && this.roomService.update(bean)==1){
        toJson(response, bean);
      }
    }
    return null;
  }
  
  /**
   * �޸�Ⱥ������,ֻ��Ⱥ�������޸�,�˴��޸ĵ�Ⱥ��Ϊ����Ⱥ
   * @param request
   * @param response
   */
  @RequestMapping("/updateDesc")
  public String updateDesc(@RequestParam String userPhone,@RequestParam String groupId,@RequestParam String desc,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room bean = this.roomService.getRoomByGroupId(groupId);
    if(bean!=null && !bean.getUserName().equals(userPhone)){
      toExMsg(response, "û���޸�Ȩ��");
      return null;
    }
    bean.setDescription(desc);
    ObjectNode datanode = JsonNodeFactory.instance.objectNode();
    datanode.put("groupname",bean.getRoomName());
    datanode.put("description",desc);
    ObjectNode node = EasemobChatGroups.updateChatGroups(datanode, groupId);
    if(node != null) {
      String statusCode = node.get("statusCode").toString();
      if("200".equals(statusCode) && this.roomService.update(bean)==1){
        toJson(response, bean);
      }
    }
    return null;
  }
  
  /**
   * �޸�Ⱥ������,ֻ��Ⱥ�������޸�,�˴��޸ĵ�Ⱥ��Ϊ����Ⱥ,����:TYPE=1,�ƾ�:TYPE=2,���ز�:TYPE=3,��ý��:TYPE=4,
   * @param request
   * @param response
   */
  @RequestMapping("/updateRoomType")
  public String updateRoomType(@RequestParam String userPhone,@RequestParam String groupId,@RequestParam int type,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room bean = this.roomService.getRoomByGroupId(groupId);
    if(bean!=null && !bean.getUserName().equals(userPhone)){
      toExMsg(response, "û���޸�Ȩ��");
      return null;
    }
    bean.setType(type);
    if(this.roomService.update(bean)==1){
      toJson(response, bean);
    }
    return null;
  }

  /**
   * ɾ��Ⱥ��,ֻ��Ⱥ������ɾ��Ⱥ��
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @RequestMapping("/deleteRoom")
  public String deleteRoom(@RequestParam String userPhone,@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room!=null&&!room.getUserName().equals(userPhone)){
      toJson(response,"û��ɾ��Ⱥ���Ȩ��");
      return null;
    }else{
      ObjectNode node = EasemobChatGroups.deleteChatGroups(groupId);
      if (node != null) {
        String statusCode = node.get("statusCode").toString();
        if ("200".equals(statusCode)) {// ���ɾ���ɹ�
          this.roomService.deleteRoomByGroupId(groupId);
          toJson(response, node.get("data").toString());
          return null;
        }
      }
    }
    return null;
  }

  /**
   * Ⱥ���Ա����[����]
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @RequestMapping("/addUser")
  public String addUser(@RequestParam String userPhone,@RequestParam String addUserName,@RequestParam String groupId,HttpServletRequest request, HttpServletResponse response)throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    ObjectNode node = EasemobChatGroups.addUserToGroup(groupId, Md5Util.getMd5Value(addUserName));
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// ������ӳɹ�
        //�򱾵����ݿ�fm_room_users�в���һ����¼����ʾ��ǰ��Ա���뵽ĳ��Ⱥ����
        RoomUsers bean =  new RoomUsers();
        bean.setGroupId(groupId);
        bean.setUserName(addUserName);
        bean.setStartdate(new Date());
        if(this.roomUsersService.save(bean)){
          List<UserBean> userList = this.userService.getRoomMembers(groupId);
          toArrayJson(response, userList);
        }
      }
    }
    return null;
  }
  
  /**
   * Ⱥ���Ա����[��������]
   * @param request
   * @param response
   * @throws IOException
   */
  @RequestMapping("/addUsers")
  public String addUsers(@RequestParam String userPhone,@RequestParam String members,@RequestParam String groupId,HttpServletRequest request, HttpServletResponse response)throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    String[] array = members.split("\\$");
    List<UserBean> userList = this.userService.getUserListByUserPhoneList(Arrays.asList(array));
    if(userList==null||userList.size()==0){
      toExMsg(response, UserCnst.USER_NOT_EXIST);
      return null;
    }
    String[] temp=new String[userList.size()];
    ArrayNode usernames = JsonNodeFactory.instance.arrayNode();
    for(int i=0;i<userList.size();i++){
      temp[i] = Md5Util.getMd5Value(userList.get(i).getUsername());
      usernames.add(temp[i]);
    }
    ObjectNode usernamesNode = JsonNodeFactory.instance.objectNode();
    usernamesNode.put("usernames", usernames);
    ObjectNode node = EasemobChatGroups.addUsersToGroupBatch(groupId, usernamesNode);
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// ������ӳɹ�
        //Ⱥ�鴴���ɹ�֮������Ⱥ���Ա���в��������Ա���˴�����������
        List<RoomUsers> list = new ArrayList<RoomUsers>();
        for(int i = 0; i < array.length; i++) {
          RoomUsers roomUsers = new RoomUsers();
          roomUsers.setMainid((long)userList.get(i).getMainid()+new Date().getTime());
          roomUsers.setGroupId(groupId);
          roomUsers.setUserName(array[i]);
          roomUsers.setStartdate(new Date());
          roomUsers.setUserRemark(userList.get(i).getNickname());
          list.add(roomUsers);
        }
        if(this.roomUsersService.addBatchRecord(list)){
          List<UserBean> userlist = this.userService.getUserListByUserPhoneList(Arrays.asList(array));
          toArrayJson(response,userlist);
        }
      }
    }
    return null;
  }
  
  /**
   * ɾ��Ⱥ���Ա�����ˡ�,ֻ��Ⱥ������ɾ����
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @RequestMapping("/deleteUser")
  public String deleteUserFromGroup(@RequestParam String userPhone,@RequestParam String delUserName,@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room!=null&&!room.getUserName().equals(userPhone)){
      toJson(response,"û��ɾ����Ա��Ȩ��");
      return null;
    }
    ObjectNode node = EasemobChatGroups.deleteUserFromGroup(groupId,Md5Util.getMd5Value(delUserName));
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// ���ɾ���ɹ�
        if(this.roomUsersService.deleteRoomUser(groupId, delUserName)){
          toJson(response, "ɾ���ɹ�");
        }
      }
    }
    return null;
  }

  /**
   * ��ȡȺ�����г�Ա(�������ݿ�)
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @RequestMapping("/getMembersFromHX")
  public String getAllMemberssByGroupId(@RequestParam String userPhone,@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    ObjectNode node = EasemobChatGroups.getAllMemberssByGroupId(groupId);
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// ��ȡ�ɹ�
        JsonNode data = node.get("data");
        List<String> userids = new ArrayList<String>();
        if (data != null) {
          Iterator<JsonNode> it = data.iterator();
          while (it.hasNext()) {
            JsonNode t = it.next();
            JsonNode u = t.get("member");
            if (u != null) {
              String userid = u.asText();
              if (userid != null) {
                userids.add(userid);
              }
            }
          }
        }
        List<UserBean> list = this.userService.getUserList(userids);
        toArrayJson(response,list);
      }
    }
    return null;
  }
  /**
   * ��ȡȺ�����г�Ա(�������ݿ�)
   * @param request
   * @param response
   * @throws IOException
   */
  @RequestMapping("/getRoomMembersFromLocal")
  public String getRoomMembersFromLocal(@RequestParam String userPhone,@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException{
    List<UserBean> userList = this.userService.getRoomMembers(groupId);
    toArrayJson(response, userList);
    return null;
  }
   
  /**�ϴ�Ⱥ��ͷ�� */
  @RequestMapping("/uploadHeadImg")
  public String uploadHeadImg(@RequestParam String groupId,@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException{  
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room==null){
      toExMsg(response,"��Ⱥ�鲻����");
      return null;
    }
    if(!room.getUserName().equals(userPhone)){
      toExMsg(response,"ֻ��Ⱥ�������ϴ�Ⱥͷ��");
      return null;
    }
    if(!myfile.isEmpty()){  
      String tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
      String  fileName = myfile.getOriginalFilename();
      String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
      String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
      //���ļ�ֻ����linux�²�������
      File file = new File(tempPath,newFileName);
      FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
      String tpUrl=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
      room.setHeadImgePath(tpUrl);
    }
    if(this.roomService.update(room)==1){
      toJson(response,room);
    }else {
      toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
    }
    return null;
  }
  
  /**����Ⱥ��ͷ�� */
  @RequestMapping("/updateHeadImg")
  public String updateHeadImg(@RequestParam String groupId,@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException{  
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room==null){
      toExMsg(response,"��Ⱥ�鲻����");
      return null;
    }
    if(!room.getUserName().equals(userPhone)){
      toExMsg(response,"ֻ��Ⱥ�������ϴ�Ⱥͷ��");
      return null;
    }
    String headImgpath = room.getHeadImgePath();
    if(!myfile.isEmpty()){  
      String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
      String  fileName = myfile.getOriginalFilename();
      String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
      String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
      //���ļ�ֻ����linux�²�������
      File file = new File(tempPath,newFileName);
      FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
      String tpUrl=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
      room.setHeadImgePath(tpUrl);
    }
    if(this.roomService.update(room)==1){
      //ɾ��nginx��������ԭ����ͷ��
      if(StringUtil.isNotNull(headImgpath)){
        headImgpath = headImgpath.replace(HTTPHEAD+NginxUtil.getNginxIP(),NginxUtil.getNginxDisk());
        File file = new File(headImgpath);
        if(file.isFile() && file.delete()){
          toJson(response,room);
        }
      }
    }else {
      toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
    }
    return null;
   }
  
   private UserBean getBeanFromRedis(String userPhone){
     String token = (String) RedisClient.get(userPhone);
     UserBean user = null;
     if(StringUtil.isNotNull(token)){
       user = (UserBean)RedisClient.getObject(token);
       if(user!=null){
         RedisClient.set(userPhone, user.getToken(),1800);
         RedisClient.setObject(user.getToken(),user,1800);
       }
     }
     return user;
   }
}