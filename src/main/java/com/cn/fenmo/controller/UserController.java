package com.cn.fenmo.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;
import com.cn.fenmo.service.IUserService;
import com.cn.fenmo.util.DateUtil;
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
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
@Controller
@RequestMapping("/user")
@Api(value = "/UserService", description = "User Service Interface API")
public class UserController extends ToJson{
    @Autowired
  	private IUserService userService;
    
    private final String HTTPHEAD="http://";
	  @RequestMapping("/getUser")
    public String showUser(HttpServletRequest request,Model model){
      String id = request.getParameter("id");
      UserBean user =  new  UserBean();
      if(StringUtil.isNumeric(id)){
         user = this.userService.getUserById(Long.parseLong(id));
      }
      model.addAttribute("user", user);
      //返回到showUser页面
      return "showUser";
      
    }
	   @RequestMapping("/indexHtml")
	   public void indexHtml(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
	     request.getRequestDispatcher("/WEB-INF/swagger/index.html").forward(request,response);
	   }
	  
	  
  	@RequestMapping("/getPage")
    public void getPage(@RequestParam String token,HttpServletRequest request,HttpServletResponse response) throws IOException{
  	   String fmNum = request.getParameter("fmNum");
  	   String phone = request.getParameter("userPhone");
  	   String age = request.getParameter("age");
  	   String sex = request.getParameter("sex");
  	   String start = request.getParameter("start");
       String limit = request.getParameter("limit");
       String nickName = request.getParameter("nickName");
       UserBean bean = (UserBean)RedisClient.getObject(token);
       if(bean==null){
         toExMsg(response,UserCnst.USER_NOT_EXIST);
         return;
       }else{
          List userList = null;
          ViewPage viewPage = new ViewPage();
          Map<String,Object> parmas = new HashMap<String, Object>();
          parmas.put("fmNum",fmNum);
          parmas.put("nickName",nickName);
          parmas.put("phone", phone);
          parmas.put("age",age);
          parmas.put("sex", sex);
          if(StringUtil.isNumeric(start)){
            parmas.put("start", Integer.parseInt(start));
          }else{
            parmas.put("start", viewPage.getPageStart());
          }
          if(StringUtil.isNumeric(limit)){
            parmas.put("limit", limit);
          }else{
            parmas.put("limit", viewPage.getPageLimit());
          }
          int count =  this.userService.selectCount(parmas);
          userList = userService.selectUserBy(parmas);
          viewPage.setTotalCount(count);
          viewPage.setListResult(userList);
          toViewPage(response,viewPage);
       }
    }
	
  	 /**用户注册
  	 * @throws Exception */
  	 @RequestMapping("/userReg")
  	 public String userReg(@RequestParam String userPhone,@RequestParam String passWord,@RequestParam int sex,@RequestParam String birthday,HttpServletRequest request,HttpServletResponse response) throws Exception{
  	   UserBean bean =  null;
  	   if(!StringUtil.isMobileNO(userPhone)){
         toExMsg(response,UserCnst.PARMARS_NOT_ALLOWED);
         return null;
       }
  	   String token = (String)RedisClient.get(userPhone);
       if(StringUtil.isNotNull(token)){    
          bean = (UserBean)RedisClient.getObject(token);
  	   }else{
  	      bean = this.userService.getUserBeanByPhone(userPhone);
  	   }
       if(bean!=null){
         toExMsg(response,UserCnst.USER_HAVE_REGISTED);
         return null;
       }else{
           ObjectNode datanode = JsonNodeFactory.instance.objectNode();
           datanode.put("username",Md5Util.getMd5Value(userPhone));
           datanode.put("password",Md5Util.getMd5Value(userPhone));
           ObjectNode objectNode = EasemobIMUsers.createNewIMUserSingle(datanode);
           if(objectNode!=null){
               String statusCode = objectNode.get("statusCode").toString();
               if("200".equals(statusCode)){
                 String tokenId = UUIDUtil.createUUID();
                 bean = new UserBean();
                 String fmNo = StringUtil.getFmNO();
                 UserBean fmBean = this.userService.getUserBeanByFmNo(fmNo);
                 while(fmBean!=null){
                   fmNo = StringUtil.getFmNO();
                   fmBean = this.userService.getUserBeanByFmNo(fmNo);
                   if(fmBean==null){
                     break;
                   }
                 }
                 bean.setPassword(passWord);
                 bean.setToken(tokenId);
                 bean.setFmNo(fmNo);
                 bean.setUsername(userPhone);
                 bean.setSex(sex);
                 Date date = DateUtil.parseDateDayFormat(birthday);
                 bean.setBirthday(date);
                 bean.setAge(DateUtil.getAge(birthday));
                 bean.setConstellation(DateUtil.getConstellation(date.getMonth(),date.getDay()));
                 bean.setPhone(Long.parseLong(userPhone));
                 if(this.userService.save(bean)){
                   toExSuccMsg(response,UserCnst.SUCCESS_REG);
                   return null;
                 }else{
                   toExMsg(response,UserCnst.INFO_SAVE_FAIL);
                 }
               }else{
                 toExMsg(response,UserCnst.HX_FRIENDER_ZC);
                 return null;
               }
            }else{
              toExMsg(response,UserCnst.HX_FRIENDER_ZC);
            }
       }
       return null;
   }
	  /**修改用户信息
	   * @throws IOException */
	  @RequestMapping("/changeUser")
   public String updateUser(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
	   UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response, UserCnst.NO_LOGIN);
       return null;
     }else{
       String message = requestToEntity(request,bean);
       if(!"".equals(message)){
         toExMsg(response,message);
         return null;
       }
       if(this.userService.update(bean)==1){
         toJson(response, bean);
         RedisClient.set(userPhone,bean.getToken(),1800);
         RedisClient.setObject(bean.getToken(),bean,1800);
       }else{
         toExMsg(response, UserCnst.INFO_UPDATE_FAIL);
       }
     }
     return null;
   }
	  
	   /**登陆
	   * @throws IOException */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation(value = "登陆", httpMethod = "POST", notes = "add user")
	  public String login(@RequestParam String userPhone,@RequestParam String passWord,HttpServletRequest request,HttpServletResponse response) throws IOException{
	    if(!StringUtil.isMobileNO(userPhone)){
	      toExMsg(response,UserCnst.PARMARS_NOT_ALLOWED);
	      return null;
	    }
      UserBean bean = this.userService.getUserBeanByPhone(userPhone); 
      if(bean==null){
        //用户不存在，提示用户注册
        toExMsg(response,UserCnst.USER_NOT_EXIST);
        return null;
      }else{
        if(!bean.getPassword().equals(passWord)){
          toExMsg(response,UserCnst.PASSWORD_ERROR);
          return null;
        }
        ObjectNode node = EasemobMessages.getUserStatus(Md5Util.getMd5Value(userPhone));
        if(node!=null){
          String statusCode = node.get("statusCode").toString();
          if("200".equals(statusCode)){
            String userStatus = node.get("data").path(Md5Util.getMd5Value(userPhone)).asText();
            if("online".equals(userStatus)){
              toJson(response, bean);
              return null;
            }else if("offline".equals(userStatus)){
              ObjectNode objectNode =  EasemobIMUsers.imUserLogin(Md5Util.getMd5Value(userPhone), Md5Util.getMd5Value(userPhone));
              if(objectNode!=null){
                String code = objectNode.get("statusCode").toString();
                if("200".equals(code)){
                  toJson(response, bean);
                  RedisClient.set(userPhone, bean.getToken(),1800);//设置token过期时间为30分钟
                  RedisClient.setObject(bean.getToken(),bean,1800);
                  return null;
                }else{
                  toExMsg(response,UserCnst.HX_FRIENDER_LOGIN);
                }
              }else{
                toExMsg(response,UserCnst.HX_FRIENDER_LOGIN);
              }
            }
          }else{
            toExMsg(response,"登陆环信服务器出错");
            return null;
          }
        }
      }
      return null;
	  }
   /**重新设置密码,用户忘记密码的情况下，通过手机号码验证，。 */
   @RequestMapping("/resetPwd")
   public String resetPwd(@RequestParam String userPhone,@RequestParam String newPwd,HttpServletRequest request,HttpServletResponse response) throws IOException{
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response,UserCnst.NO_LOGIN);
       return null;
     }else{
       bean.setPassword(newPwd);
       int count = this.userService.update(bean);
       if(count==1){
         toExSuccMsg(response,UserCnst.PASSWORD_SZ_SUCCESS);
         RedisClient.set(userPhone,bean.getToken(),1800);
         RedisClient.setObject(bean.getToken(), bean,1800);
         return null;
       }else {
         toExMsg(response,UserCnst.PASSWORD_SZ_FAIL);
       }
     }
     return null;
   }
   /**修改密码 */
   @RequestMapping("/changPwd")
   @ResponseBody
   @ApiOperation(value="修改用户密码",response=String.class,notes="根据用户名的旧密码来修改密码")
   public String changPwd(
       @ApiParam(required=true,name="userPhone",value="手机号") @RequestParam("userPhone") String phone,
       @ApiParam(required=true,name="oldPwd",value="旧密码")@RequestParam("oldPwd") String oldPwd,
       @ApiParam(required=true,name="oncePwd",value="新密码第一次") @RequestParam("oncePwd") String oncePwd,
       @ApiParam(required=true,name="twicePwd",value="新密码第二次")@RequestParam("twicePwd") String twicePwd,
       HttpServletRequest request,HttpServletResponse response) throws IOException{
       UserBean bean = getUserBeanFromRedis(phone);
       if(bean==null){
         toExMsg(response,UserCnst.NO_LOGIN);
         return null;
       }else {
          if(!bean.getPassword().equals(oldPwd)){
            toExMsg(response,UserCnst.OLD_PASSWORD_ERROR);
          }else{
              bean.setPassword(oncePwd);
              int count = this.userService.update(bean);
              if(count==1){
                toExSuccMsg(response,UserCnst.CHANGE_PASSWORD_SUCCESS);
                RedisClient.set(phone,bean.getToken(),1800);
                RedisClient.setObject(bean.getToken(), bean,1800);
              }else {
                toExMsg(response,UserCnst.CHANGE_PASSWORD_ERROR);
              }
          }
       }
       return null;
   }
   
   /**通过token获取用户 */
   @RequestMapping("/getUserByToken")
   public String getUserByToken(@RequestParam String token,HttpServletRequest request,HttpServletResponse response) throws IOException{
     boolean isSuccess = RedisClient.TestRedisIsSuccess();
     UserBean bean = null;
     if(isSuccess){
       bean = (UserBean)RedisClient.getObject(token);
     }
     if(bean==null){
       bean = this.userService.getUserByToken(token);
       if(bean==null){
         toExMsg(response,UserCnst.USER_NOT_EXIST);
         return null;
       }else{
         RedisClient.setObject(bean.getToken(), bean);
         toJson(response,bean);
       }
     }else{
       toJson(response,bean);
     }
     return null;
   }
   
   /**通过手机号获取用户信息 */
   @RequestMapping("/getUserByPhone")
   public String getUser(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PARMARS_NOT_ALLOWED);
       return null;
     }
     UserBean userBean=null;
     String token = (String)RedisClient.get(userPhone);
     if(StringUtil.isNotNull(token)){    
       userBean = (UserBean)RedisClient.getObject(token);
     }else{
       userBean = this.userService.getUserBeanByPhone(userPhone);
       if(userBean!=null){
         RedisClient.set(userPhone,userBean.getToken(),1800);
         RedisClient.setObject(userBean.getToken(), userBean,1800);
         toJson(response, userBean);
       }else{
         toExMsg(response, UserCnst.USER_NOT_EXIST);
         return null;
       }
     }
     if(userBean==null){
       toExMsg(response, UserCnst.USER_NOT_EXIST);
       return null;
     }else{
       toJson(response, userBean);
     }
     return null;
   }
   /**检测手机号是否已注册 */
   @RequestMapping("/isTelNumRegisted")
   public String isTelNumRegisted(@RequestParam String userPhone,HttpServletRequest request,HttpServletResponse response) throws IOException{
     if(!StringUtil.isMobileNO(userPhone)){
       toExMsg(response,UserCnst.PHONE_NOTCANUSER);
       return null;
     }
     UserBean bean = null;
     String token = (String)RedisClient.get(userPhone);
     if(StringUtil.isNotNull(token)){
       bean =(UserBean)RedisClient.getObject(token);
     }else{
       bean = this.userService.getUserBeanByPhone(userPhone);
     }
     if(bean!=null){
       toExMsg(response,UserCnst.USER_HAVE_REGISTED);
     }else {
       toExSuccMsg(response,UserCnst.PHONE_CANUSER);
     }
     return null;
   }
  
   /**用户上传头像*/
   @RequestMapping("/uploadTx")
   public String uploadTx(@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException{  
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response, UserCnst.NO_LOGIN);
       return null;
     }
     if(bean!=null){
       if(!myfile.isEmpty()){  
         String tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
         String  fileName = myfile.getOriginalFilename();
         String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
         SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
         String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
         //此文件只能在linux下才能生成
         File file = new File(tempPath,newFileName);
         FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
         String tpUrl=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
         bean.setHeadImgPath(tpUrl);
       }
       if(this.userService.update(bean)==1){
          toJson(response,bean);
          RedisClient.set(userPhone, bean.getToken(),1800);
          RedisClient.setObject(bean.getToken(), bean,1800);
          toExSuccMsg(response, "上传成功");
          return null;
       }else {
          toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
       }
     }
     return null;
   }
   
   /**用户上传图片，只支持一张图片上传*/
   @RequestMapping("/uploadImg")
   public String uploadImg(@RequestParam String userPhone,@RequestParam MultipartFile myfile, HttpServletRequest request,HttpServletResponse response) throws IOException{  
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response, UserCnst.NO_LOGIN);
       return null;
     }
     if(bean!=null){
       String imgUrls = bean.getImgUrls();
       if(!myfile.isEmpty()){  
         String tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
         String  fileName = myfile.getOriginalFilename();
         String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
         SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
         String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
         //此文件只能在linux下才能生成
         File file = new File(tempPath,newFileName);
         FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
         if(!"".equals(imgUrls)){
           imgUrls = imgUrls.replace("null", "");
           imgUrls=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName+";"+imgUrls;
         }else{
           imgUrls=HTTPHEAD+NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
         }
       }
       bean.setImgUrls(imgUrls);
       if(this.userService.update(bean)==1){
         toJson(response,bean);
         RedisClient.set(userPhone, bean.getToken(),1800);
         RedisClient.setObject(bean.getToken(), bean,1800);
       }else {
         toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
       }
     }
     return null;
   }
   
   
   /**用户删除图片，一次只能删除一张图片*/
   @RequestMapping("/deleteImg")
   public String deleteImg(@RequestParam String userPhone,@RequestParam String imgName,HttpServletRequest request,HttpServletResponse response) throws IOException{  
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response, UserCnst.NO_LOGIN);
       return null;
     }else{
       String  imgUrls= bean.getImgUrls();
       String temp = "";
       if(StringUtil.isNotNull(imgUrls) && imgUrls.contains(imgName)){
         temp = HTTPHEAD+NginxUtil.getNginxIP()+File.separator+userPhone+File.separator+imgName;
         imgUrls = imgUrls.replace(temp,"");
         bean.setImgUrls(imgUrls);
       }else{
         toExMsg(response, "图片不存在");
         return null;
       }
       if(this.userService.update(bean)==1){
         //接着删除nginx图片服务器上的文件
         File file = new File(temp);
         if(file.isFile() && file.delete()){
           toJson(response,bean);
           RedisClient.set(userPhone, bean.getToken(),1800);
           RedisClient.setObject(bean.getToken(), bean,1800);
         }else{
           toExMsg(response, "服务器上图片不存在");
         }
       }
     }
     return null;
   }
   /**用户更新头像*/
   @RequestMapping("/updateHeadImg")
   public String updateHeadImg(@RequestParam String userPhone,@RequestParam MultipartFile myfile,HttpServletRequest request,HttpServletResponse response) throws IOException{  
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response, UserCnst.NO_LOGIN);
       return null;
     }
     if(bean!=null){
       String headImgpath = bean.getHeadImgPath();
       String updatePath = "";
       if(!myfile.isEmpty()){  
         String tempPath = NginxUtil.getNginxDisk()+File.separatorChar+userPhone;
         String  fileName = myfile.getOriginalFilename();
         String  fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
         SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
         String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
         //此文件只能在linux下才能生成
         File file = new File(tempPath,newFileName);
         FileUtils.copyInputStreamToFile(myfile.getInputStream(),file); 
         updatePath=NginxUtil.getNginxIP()+File.separatorChar+userPhone+File.separatorChar+newFileName;
       }
       bean.setHeadImgPath(HTTPHEAD+updatePath);
       if(this.userService.update(bean)==1){
         //删除nginx服务器上原来的头像
         if(StringUtil.isNotNull(headImgpath)){
           headImgpath = headImgpath.replace(NginxUtil.getNginxIP(),NginxUtil.getNginxDisk());
         }else{
           toJson(response,bean);
           return null;
         }
         File file = new File(headImgpath);
         if(file.isFile() && file.delete()){
           toJson(response,bean);
           RedisClient.set(userPhone, bean.getToken(),1800);
           RedisClient.setObject(bean.getToken(), bean,1800);
         }
       }else {
         toExMsg(response,UserCnst.INFO_UPDATE_FAIL);
       }
     }
     return null;
   }
   
   /**根据当前用户的地址搜索10KM范围内的用户，再根据计算用户距离当前用户的距离*/
   @RequestMapping("/getNearUsers")
   public String getNearUsers(@RequestParam String userPhone,@RequestParam double lat,@RequestParam double lng,@RequestParam MultipartFile myfile,HttpServletRequest request,HttpServletResponse response) throws IOException{ 
     UserBean bean = getUserBeanFromRedis(userPhone);
     if(bean==null){
       toExMsg(response, UserCnst.NO_LOGIN);
       return null;
     }
     List<UserBean> list =  this.userService.getNearUsers(lat, lng);
     
     
     
     return null;
   } 
   
   private String requestToEntity(HttpServletRequest request,UserBean userInfo){
	   String phone =  request.getParameter("userPhone");
     if(!StringUtil.isMobileNO(phone)){
       return UserCnst.PHONE_NOTCANUSER;
     }else{
       userInfo.setPhone(Long.parseLong(phone));
       userInfo.setUsername(phone);
     }
	   String idol = request.getParameter("idol");
	   if(StringUtil.isNotBlank(idol)){
	     userInfo.setIdol(idol);
     }
//	   String sex  = request.getParameter("sex");
//	   if(StringUtil.isNumeric(sex)){
//	     userInfo.setSex(Integer.parseInt(sex));
//	   }
//	   String age = request.getParameter("age");
//	   if(StringUtil.isNumeric(age)){
//       userInfo.setAge(Integer.parseInt(age));
//     }
	   String circle = request.getParameter("circle");
	   if(StringUtil.isNumeric(circle)){
	     userInfo.setCircle(Integer.parseInt(circle));
     }
	   String nickNameString = request.getParameter("nickname");
	   if(StringUtil.isNotBlank(nickNameString)){
	     userInfo.setNickname(nickNameString);
	   }
	   String specificsign = request.getParameter("specificsign");
	   if(StringUtil.isNotBlank(specificsign)){
       userInfo.setSpecificsign(specificsign);
     }
	   String nationality = request.getParameter("nationality");
     if(StringUtil.isNotBlank(nationality)){
       userInfo.setNationality(nationality);
     }
     String occupation = request.getParameter("occupation");
     if(StringUtil.isNotBlank(occupation)){
       userInfo.setOccupation(occupation);
     }
//     String birthday = request.getParameter("birthday");
//     if(StringUtil.isNotBlank(birthday)){
//       Date dateBirthday= null;
//       try {
//        dateBirthday = DateUtil.parseDateFormat(birthday,"yyyy-MM-dd");
//        userInfo.setBirthday(dateBirthday);
//       } catch (ParseException e) {
//        e.printStackTrace();
//       }
//     }
	   return "";
	 }
   
   private UserBean getUserBeanFromRedis(String userPhone){
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
