package com.cn.fenmo.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.User;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cn.fenmo.file.NginxUtil;
import com.cn.fenmo.pojo.Result;
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.util.CNST;
import com.cn.fenmo.util.DateUtil;
import com.cn.fenmo.util.Dis;
import com.cn.fenmo.util.Md5Util;
import com.cn.fenmo.util.StringUtil;
import com.cn.fenmo.util.ToJson;
import com.cn.fenmo.util.UUIDUtil;
import com.cn.fenmo.util.UserCnst;
import com.cn.fenmo.util.ViewPage;
import com.easemob.server.httpclient.api.EasemobIMUsers;
import com.easemob.server.httpclient.api.EasemobMessages;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Controller
@RequestMapping("/user")
public class UserController extends ToJson {
  @Autowired
  private IUserService userService;

  private final String HTTPHEAD = "http://";

  @RequestMapping("/getUser")
  public String showUser(HttpServletRequest request, Model model) {
    String id = request.getParameter("id");
    UserBean user = new UserBean();
    if (StringUtil.isNumeric(id)) {
      user = this.userService.getUserById(Long.parseLong(id));
    }
    model.addAttribute("user", user);
    // 返回到showUser页面
    return "showUser";
  }

  @RequestMapping("/indexHtml")
  public void indexHtml(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/swagger/index.html").forward(
        request, response);
  }

  // 首页上显示的用户(获取三星用户以上用户按等级排序显示)
  @RequestMapping("/getHomePageUser")
  public void getHomePageUser(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    ViewPage viewPage = new ViewPage();
    List<UserBean> userList = this.userService.getUsersByStarLevel(3);
    viewPage.setListResult(userList);
    toViewPage(response, viewPage);
  }

  @RequestMapping("/getPage")
  public void getPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // searchKey可以是手机号，用户昵称，粉陌号
    String searchKey = request.getParameter("searchKey");
    String age = request.getParameter("age");
    String sex = request.getParameter("sex");
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    List<UserBean> userList = null;
    ViewPage viewPage = new ViewPage();
    Map<String, Object> parmas = new HashMap<String, Object>();
    parmas.put("searchKey", searchKey);
    parmas.put("age", age);
    if(StringUtil.isNotNull(sex)){
      parmas.put("sex", sex);
    }
    if (StringUtil.isNumeric(start)) {
      parmas.put("start", Integer.parseInt(start));
    } else {
      parmas.put("start", viewPage.getPageStart());
    }
    if (StringUtil.isNumeric(limit)) {
      parmas.put("limit", Integer.parseInt(limit));
    } else {
      parmas.put("limit", viewPage.getPageLimit());
    }
    int count = this.userService.selectCount(parmas);
    userList = userService.selectUserBy(parmas);
    viewPage.setTotalCount(count);
    viewPage.setListResult(userList);
    toViewPage(response, viewPage);
  }

  /**
   * 用户注册
   * 
   * @throws Exception
   */
  @RequestMapping(value = "userReg", method = RequestMethod.POST)
  public String userReg(@RequestParam String userPhone,@RequestParam String passWord, @RequestParam int sex,HttpServletRequest request,HttpServletResponse response) throws Exception {
    if (!StringUtil.isMobileNO(userPhone)) {
      toExMsg(response, UserCnst.PHONE_NOTCANUSER);
      return null;
    }
    UserBean bean = this.userService.getUserBeanByPhone(userPhone);
    if (bean!= null) {
      toExMsg(response, UserCnst.USER_HAVE_REGISTED);
      return null;
    } else {
	    ObjectNode node = EasemobIMUsers.getIMUsersByUserName(userPhone);
      if(node!=null){
  	   String statusCode = node.get("statusCode").toString();
  	   if("200".equals(statusCode)){
  		  toExMsg(response, UserCnst.USER_HAVE_REGISTED);
  		  return null;
  	   }
      }
      ObjectNode datanode = JsonNodeFactory.instance.objectNode();
      datanode.put("username",userPhone);
      datanode.put("password",Md5Util.getMd5Value(userPhone));
      ObjectNode objectNode = EasemobIMUsers.createNewIMUserSingle(datanode);
      if (objectNode != null) {
        String statusCode = objectNode.get("statusCode").toString();
        if ("200".equals(statusCode)) {
          String tokenId = UUIDUtil.createUUID();
          bean = new UserBean();
          String fmNo = StringUtil.getFmNO();
          UserBean fmBean = this.userService.getUserBeanByFmNo(fmNo);
          while (fmBean != null) {
            fmNo = StringUtil.getFmNO();
            fmBean = this.userService.getUserBeanByFmNo(fmNo);
            if (fmBean == null) {
              break;
            }
          }
          bean.setPassword(passWord);
          bean.setToken(tokenId);
          bean.setFmNo(fmNo);
          bean.setUsername(userPhone);
          bean.setSex(sex);
         // Date date = DateUtil.parseDateDayFormat(birthday);
         // bean.setBirthday(date);
          bean.setRegisterTime(new Date());
          bean.setStarLevel(0);
         // bean.setAge(DateUtil.getAge(birthday));
         // bean.setConstellation(DateUtil.getConstellation(date.getMonth(),date.getDay()));
          bean.setPhone(Long.parseLong(userPhone));
          if (this.userService.save(bean)) {
            toExSuccMsg(response, UserCnst.SUCCESS_REG);
            return null;
          } else {
            toExMsg(response, UserCnst.INFO_SAVE_FAIL);
          }
        } else {
          toExMsg(response, UserCnst.HX_FRIENDER_ZC);
          return null;
        }
      } else {
        toExMsg(response, UserCnst.HX_FRIENDER_ZC);
      }
    }
    return null;
  }

  /**
   * 修改用户信息
   * @throws IOException
   */
  @RequestMapping("/changeUser")
  public String updateUser(@RequestParam String userPhone,HttpServletRequest request, HttpServletResponse response)throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    } else {
      String message = requestToEntity(request, bean);
      if (!"".equals(message)) {
        toExMsg(response, message);
        return null;
      }
      if (this.userService.update(bean) == 1) {
        toJson(response, bean);
      } else {
        toExMsg(response, UserCnst.INFO_UPDATE_FAIL);
      }
    }
    return null;
  }

  /**
   * 登陆
   * @throws IOException
   */
  @ResponseBody
  @RequestMapping(value = "login", method = RequestMethod.POST)
  public String login(@RequestParam String userPhone,@RequestParam String passWord, HttpServletRequest request,HttpServletResponse response) throws IOException {
    if (!StringUtil.isMobileNO(userPhone)) {
      toExMsg(response, UserCnst.PARMARS_NOT_ALLOWED);
      return null;
    }
    UserBean bean = this.userService.getUserBeanByPhone(userPhone);
    if (bean == null) {
      // 用户不存在，提示用户注册
      toExMsg(response, UserCnst.USER_NOT_EXIST);
      return null;
    }else {
      if (!bean.getPassword().equals(passWord)) {
        toExMsg(response, UserCnst.PASSWORD_ERROR);
      }else{
        toJson(response, bean);
      }
    }
    return null;
  }

  @RequestMapping("/resetPwd")
  public String resetPwd(@RequestParam String userPhone,
      @RequestParam String newPwd, HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    } else {
      bean.setPassword(newPwd);
      int count = this.userService.update(bean);
      if (count == 1) {
        toExSuccMsg(response, UserCnst.PASSWORD_SZ_SUCCESS);
        StringUtil.setRides(userPhone, bean);
        return null;
      } else {
        toExMsg(response, UserCnst.PASSWORD_SZ_FAIL);
      }
    }
    return null;
  }

  /** 重新设置密码,用户忘记密码的情况下，通过手机号码获取验证码， */
  @RequestMapping(value = "changePwd", method = RequestMethod.POST)
  public String changePwd(@RequestParam("userPhone") String phone,
      @RequestParam("oncePwd") String oncePwd,
      @RequestParam("twicePwd") String twicePwd, HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(phone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }else if(!oncePwd.equals(twicePwd)){
      toExMsg(response, UserCnst.TWICE_PASSWORD_ERROR);
      return null;
    } else {
        bean.setPassword(oncePwd);
        int count = this.userService.update(bean);
        if (count == 1) {
          toExSuccMsg(response, UserCnst.CHANGE_PASSWORD_SUCCESS);
          StringUtil.setRides(phone, bean);
        } else {
          toExMsg(response, UserCnst.CHANGE_PASSWORD_ERROR);
        }
    }
    return null;
  }

  /** 通过token获取用户 */
  @RequestMapping("/getUserByToken")
  public String getUserByToken(@RequestParam String token,HttpServletRequest request, HttpServletResponse response)throws IOException {
    boolean isSuccess = RedisClient.TestRedisIsSuccess();
    UserBean bean = null;
    if (isSuccess) {
      bean = (UserBean) RedisClient.getObject(token);
    }
    if (bean == null) {
      bean = this.userService.getUserByToken(token);
      if (bean == null) {
        toExMsg(response, UserCnst.USER_NOT_EXIST);
        return null;
      } else {
        RedisClient.setObject(bean.getToken(), bean);
        toJson(response, bean);
      }
    } else {
      toJson(response, bean);
    }
    return null;
  }

  /** 通过手机号获取用户信息 */
  @RequestMapping("/getUserByPhone")
  public String getUser(@RequestParam String userPhone,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    if (!StringUtil.isMobileNO(userPhone)) {
      toExMsg(response, UserCnst.PARMARS_NOT_ALLOWED);
      return null;
    }
    UserBean  userBean = this.userService.getUserBeanByPhone(userPhone);
    if (userBean != null) {
      toJson(response, userBean);
    }else{
      toExMsg(response, UserCnst.USER_NOT_EXIST);
      return null;
    }
    return null;
  }

  /** 检测手机号是否已注册 */
  @RequestMapping("/isTelNumRegisted")
  public @ResponseBody
  Result<String> isTelNumRegisted(@RequestParam String userPhone,HttpServletRequest request, HttpServletResponse response)throws IOException {
    if (!StringUtil.isMobileNO(userPhone)) {
      toExMsg(response, UserCnst.PHONE_NOTCANUSER);
      return null;
    }
    UserBean bean = this.userService.getUserBeanByPhone(userPhone);
    if (bean != null) {
      return new Result<String>(UserCnst.USER_HAVE_REGISTED,UserCnst.USER_HAVE_REGISTED, 201);
    } else {
      return new Result<String>(UserCnst.PHONE_CANUSER, UserCnst.PHONE_CANUSER,200);
    }
  }

  /** 用户上传头像 */
  @RequestMapping("/uploadTx")
  public String uploadTx(@RequestParam String userPhone,
      @RequestParam MultipartFile myfile, HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    if (bean != null) {
      if (!myfile.isEmpty()) {
        String tempPath = NginxUtil.getNginxDisk() + File.separatorChar
            + userPhone;
        String fileName = myfile.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
            .toLowerCase();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = df.format(new Date()) + "_"
            + new Random().nextInt(1000) + "." + fileExt;
        // 此文件只能在linux下才能生成
        File file = new File(tempPath, newFileName);
        FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
        String tpUrl = HTTPHEAD + NginxUtil.getNginxIP() + File.separatorChar
            + userPhone + File.separatorChar + newFileName;
        bean.setHeadImgPath(tpUrl);
      }
      if (this.userService.update(bean) == 1) {
        toJson(response, bean);
      } else {
        toExMsg(response, UserCnst.INFO_UPDATE_FAIL);
      }
    }
    return null;
  }

  /** 用户上传图片，只支持一张图片上传 */
  @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
  public String uploadImg(@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    }
    if (bean != null) {
      String imgUrls = bean.getImgUrls();
      if (!myfile.isEmpty()) {
        String tempPath = NginxUtil.getNginxDisk() + File.separatorChar+ userPhone;
        String fileName = myfile.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = df.format(new Date()) + "_"+ new Random().nextInt(1000) + "." + fileExt;
        // 此文件只能在linux下才能生成
        File file = new File(tempPath, newFileName);
        FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
        if (!"".equals(imgUrls) && !";".equals(imgUrls)) {
          imgUrls = imgUrls+";"+HTTPHEAD + NginxUtil.getNginxIP() + File.separatorChar+ userPhone + File.separatorChar + newFileName;
        } else {
          imgUrls = HTTPHEAD + NginxUtil.getNginxIP() + File.separatorChar+ userPhone + File.separatorChar + newFileName;
        }
      }
      bean.setImgUrls(imgUrls);
      String[] urls =  imgUrls.split(";");
      if(urls.length>0){
        bean.setHeadImgPath(urls[0]);
      }
      if (this.userService.update(bean) == 1) {
        toJson(response, bean);
        RedisClient.set(userPhone, bean.getToken(), CNST.TOKEN_CANCEL);
        RedisClient.setObject(bean.getToken(), bean, CNST.TOKEN_CANCEL);
      } else {
        toExMsg(response, UserCnst.INFO_UPDATE_FAIL);
      }
    }
    return null;
  }

  /** 用户删除图片，一次只能删除一张图片 */
  @RequestMapping("/deleteImg")
  public String deleteImg(@RequestParam String userPhone,@RequestParam String imgName, HttpServletRequest request,HttpServletResponse response) throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    } else {
      String imgUrls = bean.getImgUrls();
      String temp = "";
      if (StringUtil.isNotNull(imgUrls) && imgUrls.contains(imgName)) {
        temp = HTTPHEAD + NginxUtil.getNginxIP() + File.separator + userPhone
            + File.separator + imgName;
        if (imgUrls.contains(";")) {
          imgUrls = imgUrls.replace(temp + ";", "");
        } else {
          imgUrls = imgUrls.replace(temp, "");
        }
        if (!imgUrls.contains("/")) {
          bean.setImgUrls("");
        }
        bean.setImgUrls(imgUrls);
        String[] urls =  imgUrls.split(";");
        if(urls.length>0){
          bean.setHeadImgPath(urls[0]);
        }
      } else {
        toExMsg(response, "图片不存在");
        return null;
      }
      if (this.userService.update(bean) == 1) {
        // 接着删除nginx图片服务器上的文件
        File file = new File(NginxUtil.getNginxDisk() + File.separatorChar+ userPhone + File.separator + imgName);
        if (file.isFile() && file.delete()) {
          RedisClient.set(userPhone, bean.getToken(),  CNST.TOKEN_CANCEL);
          RedisClient.setObject(bean.getToken(), bean,  CNST.TOKEN_CANCEL);
          toJson(response, bean);
        } else {
          toExMsg(response, "删除服务器上的图片失败");
        }
      }
    }
    return null;
  }

//  /** 用户更新头像 */
//  @RequestMapping(value = "updateHeadImg", method = RequestMethod.POST)
//  public String updateHeadImg(@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException {
//    UserBean bean = getUserBeanFromRedis(userPhone);
//    if (bean == null) {
//      toExMsg(response, UserCnst.NO_LOGIN);
//      return null;
//    }
//    if (bean != null) {
//      String headImgpath = bean.getHeadImgPath();
//      String updatePath = "";
//      if (!myfile.isEmpty()) {
//        String tempPath = NginxUtil.getNginxDisk() + File.separatorChar+ userPhone;
//        String fileName = myfile.getOriginalFilename();
//        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
//        // 此文件只能在linux下才能生成
//        File file = new File(tempPath, newFileName);
//        FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
//        updatePath = NginxUtil.getNginxIP() + File.separatorChar + userPhone+ File.separatorChar + newFileName;
//      }
//      bean.setHeadImgPath(HTTPHEAD + updatePath);
//      if (this.userService.update(bean) == 1) {
//        // 删除nginx服务器上原来的头像
//        if (StringUtil.isNotNull(headImgpath)) {
//          headImgpath = headImgpath.replace(NginxUtil.getNginxIP(),
//              NginxUtil.getNginxDisk());
//        } else {
//          toJson(response, bean);
//          return null;
//        }
//        File file = new File(headImgpath);
//        if (file.isFile() && file.delete()) {
//          toJson(response, bean);
//          RedisClient.set(userPhone, bean.getToken(), CNST.TOKEN_CANCEL);
//          RedisClient.setObject(bean.getToken(), bean, CNST.TOKEN_CANCEL);
//        }
//      } else {
//        toExMsg(response, UserCnst.INFO_UPDATE_FAIL);
//      }
//    }
//    return null;
//  }

  /** 根据当前用户的地址搜索10KM范围内的用户， */
  @RequestMapping("/getNearUsers")
  public String getNearUsers(@RequestParam String userPhone,
      @RequestParam double lat, @RequestParam double lng,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    } else {
      bean.setLat(lat);
      bean.setLng(lng);
      this.userService.update(bean);
    }
    String sex = request.getParameter("sex");
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    ViewPage viewPage = new ViewPage();
    Map<String, Object> params = new HashMap<String, Object>();
    if (!StringUtil.isNumeric(start)) {
      params.put("start", viewPage.getPageStart());
    } else {
      params.put("start", Integer.valueOf(start));
      viewPage.setPageStart(Integer.valueOf(start));
    }
    if (!StringUtil.isNumeric(limit)) {
      params.put("limit", viewPage.getPageLimit());
    } else {
      params.put("limit", Integer.valueOf(limit));
      viewPage.setPageLimit(Integer.valueOf(limit));
    }
    if (StringUtil.isNumeric(sex)) {
      params.put("sex", sex);
    }
    params.put("lat", lat);
    params.put("lng", lng);
    List<UserBean> list = this.userService.getNearUsers(params);
    for (int i = 0; i < list.size(); i++) {
      UserBean userBean = list.get(i);
      double lat2 = userBean.getLat();
      double lng2 = userBean.getLng();
      if (lat2 != 0 && lng2 != 0) {
        double distance = Dis.getDistance(lat2, lng2, lat, lng);
        userBean.setDistance(distance);
      }
    }
    if (list != null) {
      viewPage.setTotalCount(list.size());
      viewPage.setListResult(list);
    }
    toViewPage(response, viewPage);
    return null;
  }

  @RequestMapping(value = "updateLocation", method = RequestMethod.POST)
  public String updateLocation(@RequestParam String userPhone,
      @RequestParam double lat, @RequestParam double lng,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    UserBean bean = getUserBeanFromRedis(userPhone);
    if (bean == null) {
      toExMsg(response, UserCnst.NO_LOGIN);
      return null;
    } else {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("lat", lat);
      params.put("lng", lng);
      params.put("userPhone", bean.getPhone());
      if (this.userService.updateLocation(params) == 1) {
        toExSuccMsg(response, "位置更新成功");
      }
    }
    return null;
  }
 
  
  @RequestMapping(value = "getListUsers", method = RequestMethod.GET)
  public String getListUsers(@RequestParam String userPhones,HttpServletRequest request, HttpServletResponse response)throws IOException {
    String[] userPhoneAry = userPhones.split(",");
    List<UserBean> userList = this.userService.getUserListByUserPhoneList(Arrays.asList(userPhoneAry));
    toArrayJson(response, userList);
    return null;
  }

  private String requestToEntity(HttpServletRequest request, UserBean userInfo) {
    String phone = request.getParameter("userPhone");
    if (!StringUtil.isMobileNO(phone)) {
      return UserCnst.PHONE_NOTCANUSER;
    } else {
      userInfo.setPhone(Long.parseLong(phone));
      userInfo.setUsername(phone);
    }
    String birthday = request.getParameter("birthday");
    if (StringUtil.isNotBlank(birthday)) {
      Date date=null;
      try {
        date = DateUtil.parseDateDayDashFormat(birthday);
        userInfo.setBirthday(date);
        userInfo.setAge(DateUtil.getAge(birthday));
        int month = date.getMonth() + 1;
        int day = date.getDate();
        userInfo.setConstellation(DateUtil.getConstellation(month,day));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    String idol = request.getParameter("idol");
    if (StringUtil.isNotBlank(idol)) {
      userInfo.setIdol(idol);
    }
    String circle = request.getParameter("circle");
    if (StringUtil.isNumeric(circle)) {
      userInfo.setCircle(Integer.parseInt(circle));
    }
    String nickNameString = request.getParameter("nickname");
    if (StringUtil.isNotBlank(nickNameString)) {
      userInfo.setNickname(nickNameString);
    }
    String specificsign = request.getParameter("specificsign");
    if (StringUtil.isNotBlank(specificsign)) {
      userInfo.setSpecificsign(specificsign);
    }
    String nationality = request.getParameter("nationality");
    if (StringUtil.isNotBlank(nationality)) {
      userInfo.setNationality(nationality);
    }
    String occupation = request.getParameter("occupation");
    if (StringUtil.isNotBlank(occupation)) {
      userInfo.setOccupation(occupation);
    }
    return "";
  }

  private UserBean getUserBeanFromRedis(String userPhone) {
    UserBean user = this.userService.getUserBeanByPhone(userPhone);
//    String token = (String) RedisClient.get(userPhone);
//    if (StringUtil.isNotNull(token)) {
//      user = (UserBean) RedisClient.getObject(token);
//      if (user != null) {
//        RedisClient.set(userPhone, user.getToken(), CNST.TOKEN_CANCEL);
//        RedisClient.setObject(user.getToken(), user, CNST.TOKEN_CANCEL);
//      }
//    }
    return user;
  }
}
