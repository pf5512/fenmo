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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.Result;
import com.cn.fenmo.pojo.Room;
import com.cn.fenmo.pojo.RoomBjImg;
import com.cn.fenmo.pojo.RoomUsers;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.IRoomService;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.service.RoomBjImgService;
import com.cn.fenmo.service.RoomUsersService;
import com.cn.fenmo.util.CNST;
import com.cn.fenmo.util.Md5Util;
import com.cn.fenmo.util.RequestUtil;
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
  @Autowired
  private RoomBjImgService roomBjImgService;
  
  private final String HTTPHEAD="http://";
  
  /**
   * 上传群组背景图
   */
  @RequestMapping(value = "uploadBjImg", method = RequestMethod.POST)
  public String uploadBjImg(@RequestParam String userPhone,@RequestParam String groupId,@RequestParam String tpUrl,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    String oldPath="";
    RoomBjImg roomBjImg = this.roomBjImgService.getBean(userPhone,groupId);
    if(roomBjImg!=null){
      oldPath = roomBjImg.getBjImgUrl();
      roomBjImg.setBjImgUrl(tpUrl);
      //删除nginx服务器上原来的背景
      if(StringUtil.isNotNull(oldPath)){
        oldPath = oldPath.replace(HTTPHEAD+NginxUtil.getNginxIP(),NginxUtil.getNginxDisk());
        File file = new File(oldPath);
        if(file.isFile() && file.delete()&&this.roomBjImgService.update(roomBjImg)){
          toJson(response,roomBjImg);
          return null;
        }
      }
    }else{
      roomBjImg = new RoomBjImg();
      roomBjImg.setId(new Date().getTime());
      roomBjImg.setBjImgUrl(tpUrl);
      roomBjImg.setUserName(userPhone);
      roomBjImg.setGroupId(groupId);
      if(this.roomBjImgService.save(roomBjImg)){
        toJson(response,roomBjImg);
      }
    }
    return null;
  }
  
  /**
   * 获取群组详细信息，没有登陆也可以获取群组信息
   */
  @RequestMapping("/getRoomById")
  public String getRoomById(@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room!=null){
      toJson(response, room);
    }else{
      toExMsg(response, "群组不存在");
    }
    return null;
  }
  
  /**
   * 获取群组背景图片(备选)
   */
  @RequestMapping("/getRoomBjImgs")
  public String getRoomBjImgs(HttpServletRequest request,HttpServletResponse response) throws IOException {
    List<String> list = new ArrayList<String>();
    list.add("http://60.190.243.154/18680683004/20160417105803_81.jpg");
    list.add("http://60.190.243.154/18680683004/20160415162926_732.jpg");
    toArrayJson(response, list);
    return null;
  }
  
  /**
   * 获取群组详细信息，主要在于获取群组的背景图片
   */
  @RequestMapping("/getRoomByUserPhone")
  public String getRoomByUserPhone(@RequestParam String groupId,@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("groupId", groupId);
    params.put("userPhone", userPhone);
    RoomBjImg roomBjImg = this.roomBjImgService.getBean(userPhone,groupId);
    if(roomBjImg==null){
      Room room = this.roomService.getRoomByGroupId(groupId);
      if(room!=null){
        toJson(response, room);
      }else{
        toExMsg(response, "群组不存在");
      }
    }else{
      Room room = this.roomService.getRoomByParams(params);
      if(room!=null){
        toJson(response, room);
      }else{
        toExMsg(response, "群组不存在");
      }
    }
    return null;
  }
  
  /**
   * 推荐群组
   */
  @RequestMapping("/getHotRooms")
  public String getHotRooms(HttpServletRequest request,HttpServletResponse response) throws IOException {
    List<Room> rooms = this.roomService.getHotRooms();
    if(rooms!=null){
      toArrayJson(response,rooms);
    }else{
      toExMsg(response, "群组不存在");
    }
    return null;
  }

  /**
   * 根据群组名称搜索出来的群组，无需登陆
   * 根据群组类型(娱乐，财经，房地产，自媒体)获取群组,无需登陆
   * 此处只查找公开群
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
   * 获取用户加入的群组（根据用户userPhone获取）
   * 此处查找公开和私有群
   */
  @RequestMapping("/getPageRoomsByUserPhone")
  public String getPageRoomsByUserPhone(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException {
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
      params.put("userName",userPhone);
    }
    List<Room> roomlist = new ArrayList<Room>();
    int count = this.roomService.getMyJoinRoomCount(params);
    if(count>0){
      roomlist =  (List<Room>) this.roomService.getMyJoinRoom(params);
      viewPage.setTotalCount(count);
      viewPage.setListResult(roomlist);
    }
    toViewPage(response, viewPage);
    return null;
  }
  
  /**
   * 获取显示在主页面上的热门群组(默认显示管理员用户创建的群组，根据管理员userName搜索)
   * 此处只查找公开群
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
   * 新建群(经过创建群组界面创建),只有登陆才能新建,此处创建的群是公开群，按群组名称可以搜索到,
   * @param request
   * @param response
   */
  @RequestMapping("/createPublicRoom")
  public String createPublicRoom(@RequestParam String headImgeUrl,@RequestParam String userPhone,@RequestParam int type,@RequestParam String roomName,@RequestParam String desc,@RequestParam String subject,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    //此方法只查找公开群
    List<Room> roomList = this.roomService.getRoomByName(roomName);
    for(int i=0;roomList!=null&&i<roomList.size();i++){
      Room room = roomList.get(i);
      if(room.getUserName().equals(userPhone)){
        toExMsg(response, "你已经创建了一个叫"+roomName+"的群");
        return null;
      }
    }
    ObjectNode datanode = JsonNodeFactory.instance.objectNode();
    datanode.put("groupname",roomName);
    datanode.put("desc",desc);
    // 群组类型： true 公开群，false 私有群,
    datanode.put("public",true);
    datanode.put("maxusers",300);
    // 此属性为必选的，此值为false时，加群不需要群主批准,为true时需要群主审批
    datanode.put("approval",false);
    // true 允许群成员邀请人加入此群;
    datanode.put("allowinvites", true);
    //是否只有群成员可以进来发言，true 是 ， false 否
    datanode.put("membersonly", false );
    datanode.put("owner",userPhone);
    ObjectNode node = EasemobChatGroups.creatChatGroups(datanode);
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// 如果新建成功
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
        bean.setMaxusers(300);
        bean.setCreatedate(new Date());
        bean.setHeadImgePath(headImgeUrl);
        if(this.roomService.save(bean)){
          toJson(response, bean);
          //向本地数据库fm_room_users中插入一条记录（把自己加入到群组中）
          RoomUsers roomUser=  new RoomUsers();
          roomUser.setGroupId(groupId);
          roomUser.setUserName(userPhone);
          roomUser.setStartdate(new Date());
          this.roomUsersService.save(roomUser);
        }
      }else if("400".equals(statusCode)){
        String error_description = node.get("error_description").toString();
        toExMsg(response, error_description);
      }
    }
    return null;
  }
  
  
  /**
   * 新建群,后台创建
   * @param request
   * @param response
   */
  @RequestMapping("/createPublicRoomForWeb")
  public String createPublicRoomForWeb(@RequestParam MultipartFile myfile,@RequestParam int type,@RequestParam String roomName,@RequestParam String desc,@RequestParam String subject,HttpServletRequest request,HttpServletResponse response) throws IOException {
    UserBean  userbean  = RequestUtil.getLoginUser(request);
    List<Room> roomList = this.roomService.getRoomByName(roomName);
    for(int i=0;roomList!=null&&i<roomList.size();i++){
      Room room = roomList.get(i);
      if(room.getUserName().equals(userbean.getUsername())){
        toExMsg(response, "你已经创建了一个叫"+roomName+"的群");
        return null;
      }
    }
    ObjectNode datanode = JsonNodeFactory.instance.objectNode();
    datanode.put("groupname",roomName);
    datanode.put("desc",desc);
    // 群组类型： true 公开群，false 私有群,
    datanode.put("public",true);
    datanode.put("maxusers",300);
    // 此属性为必选的，此值为false时，加群不需要群主批准,为true时需要群主审批
    datanode.put("approval",false);
    // true 允许群成员邀请人加入此群;
    datanode.put("allowinvites", true);
    //是否只有群成员可以进来发言，true 是 ， false 否
    datanode.put("membersonly", false );
    datanode.put("owner",userbean.getUsername());
    ObjectNode node = EasemobChatGroups.creatChatGroups(datanode);
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// 如果新建成功
        JsonNode data = node.get("data");
        String groupId = data.get("groupid").asText();
        Room bean = new Room();
        bean.setGroupId(groupId);
        bean.setUserName(userbean.getUsername());
        bean.setRoomName(roomName);
        bean.setDescription(desc);
        bean.setSubject(subject);
        bean.setType(type);
        bean.setIspublic(RoomCnst.ROOM_PUBLIC);
        bean.setUserCounts(1);
        bean.setMaxusers(300);
        bean.setCreatedate(new Date());
        if(!myfile.isEmpty()){  
            String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userbean.getUsername();
            String  fileName = myfile.getOriginalFilename();
            String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            //此文件只能在linux下才能生成
            File file = new File(tempPath,newFileName);
            FileUtils .copyInputStreamToFile(myfile.getInputStream(),file); 
            String tpUrl=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userbean.getUsername()+File.separatorChar+newFileName;
            bean.setHeadImgePath(tpUrl);
        }
        this.roomService.save(bean);
        //向本地数据库fm_room_users中插入一条记录（把自己加入到群组中）
        RoomUsers roomUser=  new RoomUsers();
        roomUser.setGroupId(groupId);
        roomUser.setUserName(userbean.getUsername());
        roomUser.setStartdate(new Date());
        this.roomUsersService.save(roomUser);
      }
    }
    return null;
  }
  
  
  /**
   * 新建群(私有群，发起群聊时创建的群),只有登陆才能新建,按群组名称不可以搜索到,但是获取我的群组可以获取到
   * @param request
   * @param response
   */
  @RequestMapping("/createPrivateRoom")
  public String createPrivateRoom(@RequestParam String userPhone,@RequestParam String members,HttpServletRequest request,HttpServletResponse response) throws IOException {
    UserBean userbean = getBeanFromRedis(userPhone);
    if(userbean==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    ObjectNode datanode = JsonNodeFactory.instance.objectNode();
    datanode.put("groupname","群聊");
    datanode.put("desc","暂无");
    // 群组类型： true 公开群，false 私有群,
    datanode.put("public",false);
    datanode.put("maxusers",100);
    // 此属性为必选的，此值为false时，加群不需要群主批准,为true时需要群主审批
    datanode.put("approval",true);
    // true 允许群成员邀请人加入此群;
    datanode.put("allowinvites",true);
    //是否只有群成员可以进来发言，true 是 ， false 否
    datanode.put("membersonly",true);
    datanode.put("owner",userPhone);
    // 群组成员members，  群组成员为以$符号分隔的username字符串
    String[] array = null;
    List<UserBean> userList = new ArrayList<UserBean>();
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
        temp[i] =userList.get(i).getUsername();
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
      if("200".equals(statusCode)) {// 如果新建成功
        JsonNode data = node.get("data");
        String groupId = data.get("groupid").asText();
        Room bean = new Room();
        bean.setGroupId(groupId);
        bean.setUserName(userPhone);
        bean.setRoomName("群聊");
        bean.setIspublic(RoomCnst.ROOM_PRIVATE);
        bean.setCreatedate(new Date());
        bean.setUserCounts(array.length+1);
        if(this.roomService.save(bean)){
          //群组创建成功之后再往群组成员表中插入各个成员，此处是批量插入
          List<RoomUsers> list = new ArrayList<RoomUsers>();
          RoomUsers roomUser = new RoomUsers();
          roomUser.setMainid(new Date().getTime()+new Random().nextLong());
          roomUser.setGroupId(groupId);
          roomUser.setUserName(userPhone);
          roomUser.setStartdate(new Date());
          roomUser.setUserRemark("".equals(userbean.getNickname())?userPhone:userbean.getNickname());
          list.add(roomUser);
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
  
//  /**
//   * 修改用户在群组中的昵称(只在该群组中显示)
//   */
//  @RequestMapping("/updateUserRemarkInRoom")
//  public String updateUserRemarkInRoom(@RequestParam String userPhone,@RequestParam String groupId,@RequestParam String userRemark,HttpServletRequest request,HttpServletResponse response) throws IOException {
//    if(getBeanFromRedis(userPhone)==null){
//      toExMsg(response, UserCnst.NO_LOGIN);
//      return null;
//    }
//    RoomUsers roomUsers =  this.roomUsersService.getRoomUsers(userPhone,groupId);
//    if(roomUsers!=null){
//      roomUsers.setUserRemark(userRemark);
//      if(this.roomUsersService.updateRoomUser(roomUsers)){
//        toJson(response, roomUsers);
//      }else{
//        toExMsg(response, "更新失败");
//      }
//    }
//    return null;
//  }

  /**
   * 修改群聊名称,修改群组的roomName只有群主才能修改,不管是公有群还是私有群都适用
   * @param request
   * @param response
   */
  @RequestMapping(value = "updateRoomName", method = RequestMethod.POST)
  public String updateRoomName(@RequestParam String userPhone,@RequestParam String groupId,@RequestParam String roomName,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room bean = this.roomService.getRoomByGroupId(groupId);
    if(bean!=null && !bean.getUserName().equals(userPhone)){
      toExMsg(response, "没有修改权限");
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
   * 修改群组描述,只有群主才能修改,此处修改的群组为公开群
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
      toExMsg(response, "没有修改权限");
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
   * 修改群组类型,只有群主才能修改,此处修改的群组为公开群,娱乐:TYPE=1,财经:TYPE=2,房地产:TYPE=3,自媒体:TYPE=4,
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
      toExMsg(response, "没有修改权限");
      return null;
    }
    bean.setType(type);
    if(this.roomService.update(bean)==1){
      toJson(response, bean);
    }
    return null;
  }

  /**
   * 删除群组,只有群主才能删除群组
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
      toJson(response,"没有删除群组的权限");
      return null;
    }else{
      ObjectNode node = EasemobChatGroups.deleteChatGroups(groupId);
      if (node != null) {
        String statusCode = node.get("statusCode").toString();
        if ("200".equals(statusCode)) {// 如果删除成功
          this.roomService.deleteRoomByGroupId(groupId);
          toJson(response, node.get("data").toString());
          return null;
        }
      }
    }
    return null;
  }

  /**
   * 群组成员加入[单人]
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @RequestMapping("/addUser")
  public String addUser(@RequestParam String userPhone,@RequestParam String addUserName,@RequestParam String groupId,HttpServletRequest request, HttpServletResponse response)throws IOException {
    UserBean user = getBeanFromRedis(userPhone);
    if(user==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    RoomUsers  usermember = this.roomUsersService.getRoomUsers(userPhone, groupId);
    if(usermember==null){
      toExMsg(response, "邀请者不在群组中无法邀请");
      return null;
    }
    RoomUsers  member = this.roomUsersService.getRoomUsers(addUserName, groupId);
    if(member!=null){
      toExMsg(response, "群组中已经有该成员了");
      return null;
    }
    ObjectNode node = EasemobChatGroups.addUserToGroup(groupId, addUserName);
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// 如果添加成功
        //向本地数据库fm_room_users中插入一条记录，表示当前成员加入到某个群里面
        RoomUsers bean =  new RoomUsers();
        bean.setGroupId(groupId);
        bean.setUserName(addUserName);
        bean.setStartdate(new Date());
        bean.setUserRemark(addUserName);
        if(this.roomUsersService.save(bean)){
          toExSuccMsg(response, userPhone+"邀请了"+addUserName+"进入群聊");
        }else{
          toExSucc(response,false);
        }
      }
    }else{
      toExMsg(response, "群组中已经有该成员了");
      return null;
    }
    return null;
  }
  
  /**
   * 群组成员加入[批量加入]
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
    RoomUsers  usermember = this.roomUsersService.getRoomUsers(userPhone, groupId);
    if(usermember==null){
      toExMsg(response, "邀请者不在群组中无法邀请");
      return null;
    }
    String[] array = members.split("\\$");
    //排除群组中已经有的成员
    List<String> listStr = Arrays.asList(array);
    List<String> arrayList = new ArrayList<String>(listStr);
    for(int i=0;i<listStr.size();i++){
      RoomUsers member = this.roomUsersService.getRoomUsers(listStr.get(i), groupId);
      if(member!=null){
        arrayList.remove(i);
      }
    }
    List<UserBean> userList = this.userService.getUserListByUserPhoneList(arrayList);
    if(userList==null||userList.size()==0){
      toExMsg(response, UserCnst.USER_NOT_EXIST);
      return null;
    }
    String[] temp=new String[userList.size()];
    ArrayNode usernames = JsonNodeFactory.instance.arrayNode();
    for(int i=0;i<userList.size();i++){
      temp[i] = userList.get(i).getUsername();
      usernames.add(temp[i]);
    }
    ObjectNode usernamesNode = JsonNodeFactory.instance.objectNode();
    usernamesNode.put("usernames", usernames);
    ObjectNode node = EasemobChatGroups.addUsersToGroupBatch(groupId, usernamesNode);
    if (node != null) {
      String statusCode = node.get("statusCode").toString();
      if ("200".equals(statusCode)) {// 如果添加成功
        //群组创建成功之后再往群组成员表中插入各个成员，此处是批量插入
        List<RoomUsers> list = new ArrayList<RoomUsers>();
        for(int i = 0; i <arrayList.size(); i++) {
          RoomUsers roomUsers = new RoomUsers();
          roomUsers.setMainid((long)userList.get(i).getMainid()+new Date().getTime());
          roomUsers.setGroupId(groupId);
          roomUsers.setUserName(userList.get(i).getUsername());
          roomUsers.setStartdate(new Date());
          roomUsers.setUserRemark(userList.get(i).getNickname());
          list.add(roomUsers);
        }
        if(this.roomUsersService.addBatchRecord(list)){
          List<UserBean> userlist = this.userService.getUserListByUserPhoneList(Arrays.asList(array));
          toArrayJson(response,userlist);
        }
      }
    }else{
      toExMsg(response,"用户已经在群组中");
    }
    return null;
  }
  
  /*
   * 获取当前用户所有群组中群组成员最多的群组
   * */
  @RequestMapping("/getMaxUsersRoom")
  public @ResponseBody Result<Room> getMaxUsersRoom(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     Room bean  = this.roomService.getMaxUseRoom(userPhone);
     if(bean!=null){
       return new Result<Room>(bean,"success", 200);
     }
     return new Result<Room>(bean,"fail", 201);
  }

  /**
   * 删除群组成员【单人】,(1)群主可以删人，自己可以主动退出
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
      ObjectNode node = EasemobChatGroups.deleteUserFromGroup(groupId,delUserName);
      if (node != null) {
        String statusCode = node.get("statusCode").toString();
        if ("200".equals(statusCode)) {// 如果删除成功
          if(this.roomUsersService.deleteRoomUser(groupId, delUserName)){
            toJson(response, "删除成功");
          }
        }
      }
    }else {
      toJson(response,"没有删除成员的权限");
      return null;
    }
    return null;
  }

  
  /**
   * 自己主动退出群组;
   * @throws IOException
   */
  @RequestMapping(value = "exitFromGroup", method = RequestMethod.POST)
  public String exitFromGroup(@RequestParam String userPhone,@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException {
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room!=null){
      ObjectNode node = EasemobChatGroups.deleteUserFromGroup(groupId,userPhone);
      if (node != null) {
        String statusCode = node.get("statusCode").toString();
        if ("200".equals(statusCode)) {// 如果删除成功
          if(this.roomUsersService.deleteRoomUser(groupId, userPhone)){
            toJson(response, "退出成功");
          }
        }
      }
    }else {
      toJson(response,"群组不存在");
      return null;
    }
    return null;
  }
  /**
   * 获取群组所有成员(环信数据库)
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
      if ("200".equals(statusCode)) {// 获取成功
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
   * 获取群组所有成员(本地数据库)
   * @param request
   * @param response
   * @throws IOException
   */
  @RequestMapping("/getRoomMembersFromLocal")
  public String getRoomMembersFromLocal(@RequestParam String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException{
    String searchKey = request.getParameter("searchKey");
    Map<String,Object> parmas =  new HashMap<String, Object>();
    parmas.put("groupId", groupId);
    if(StringUtil.isNotNull(searchKey)){
      parmas.put("searchKey", searchKey);
    }
    List<UserBean> userList = this.userService.getRoomMembers(parmas);
    toArrayJson(response, userList);
    return null;
  }
   
  /**上传群组头像 */
  @RequestMapping(value = "uploadHeadImg", method = RequestMethod.POST)
  public String uploadHeadImg(@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException{  
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    if(!myfile.isEmpty()){  
      String  tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
      String  fileName = myfile.getOriginalFilename();
      String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
      String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
      //此文件只能在linux下才能生成
      File file = new File(tempPath,newFileName);
      FileUtils .copyInputStreamToFile(myfile.getInputStream(),file); 
      String tpUrl=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
      toExSuccMsg(response, tpUrl);
    }
    return null;
  }
  
  /**更新群组头像 */
  @RequestMapping("/updateHeadImg")
  public String updateHeadImg(@RequestParam String groupId,@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException{  
    if(getBeanFromRedis(userPhone)==null){
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    Room room = this.roomService.getRoomByGroupId(groupId);
    if(room==null){
      toExMsg(response,"该群组不存在");
      return null;
    }
    if(!room.getUserName().equals(userPhone)){
      toExMsg(response,"只有群主才能上传群头像");
      return null;
    }
    String headImgpath = room.getHeadImgePath();
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
      room.setHeadImgePath(tpUrl);
    }
    if(this.roomService.update(room)==1){
      //删除nginx服务器上原来的头像
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
     UserBean user = this.userService.getUserBeanByPhone(userPhone);
     return user;
   }
   
   
   /**
    * for web 接口
    */
   @RequestMapping("/searchPageRooms")
   public String searchPageRooms(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
       params.put("type",Integer.parseInt(type)==0?null:Integer.parseInt(type));
     }
     params.put("ispublic",RoomCnst.ROOM_PUBLIC);
     int count = this.roomService.selectCount(params);
     List<Room> roomlist  =  (List<Room>) this.roomService.getRooms(params);
     toViewPageForWeb(response,roomlist,count);
     return null;
   }
}
