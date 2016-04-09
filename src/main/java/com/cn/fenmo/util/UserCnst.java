package com.cn.fenmo.util;

public class UserCnst {
  public final static int MAN               = 0;  //男
  public final static int WOMAN             = 1;  //女
  
  public final static int REQUEST_SQ             = 1;  //请求申请状态,
  public final static int REQUEST_JJ             = 2;  //请求拒绝了,两人还是陌生人
  public final static int REQUEST_TG             = 3;  //请求通过了,两人成为了好友
  public final static String FRIENDER_STR    = "好友";
  public final static String STRANGER_STR    = "陌生人";
  
  public final static String NO_LOGIN           = "用户没有登陆";
  public final static String USER_NOT_EXIST     = "用户不存在";
  public final static String ADD_USER_NOT_EXIST = "添加的用户不存在";
  public final static String PASSWORD_ERROR     = "密码错误";
  public final static String PASSWORD_NO_JM     = "密码没加密";
  public final static String USER_HAVE_REGISTED = "该手机号已经被注册";
  public final static String PHONE_CANUSER = "该手机号可用";
  public final static String PHONE_NOTCANUSER = "该手机号不可用";
  public final static String CHANGE_PASSWORD_ERROR="密码修改失败";
  public final static String CHANGE_PASSWORD_SUCCESS="密码修改成功";
  public final static String OLD_PASSWORD_ERROR ="原密码错误";
  public final static String USER_DEACTIVE      ="该用户被系统冻结";
  public final static String TOKEN_ERROR        ="token值错误";
  public final static String TOKEN_NULL         ="token值为空";
  public final static String PARMARS_NOT_ALLOWED="数据不合法";
  public final static String PASSWORD_NOT_ALLOWED="密码必须是数字或者字母组成的6-18的字符串";
  public final static String TWICE_PASSWORD_ERROR="两次输入的密码不一致";
  public final static String SUCCESS_REG="用户注册成功";
  
  public final static String PASSWORD_SZ_SUCCESS="密码重置成功";
  public final static String PASSWORD_SZ_FAIL="密码重置失败";
  public final static String INFO_UPDATE_FAIL="数据更新失败";
  public final static String INFO_SAVE_FAIL="数据保存失败";
  public final static String FRIENDER_ADD="添加好友请求失败";
  public final static String HX_FRIENDER_PASS="环信通过好友请求失败";
  public final static String HX_FRIENDER_DELETE="环信删除好友失败";
  public final static String HX_FRIENDER_ZC="环信服务器上注册失败";
  public final static String HX_FRIENDER_LOGIN="登陆环信服务器失败";
  public final static String FRIENDER_DELETE_SUCCESS="删除好友成功";
  public final static String FRIENDER_DELETE_FAIL="本地删除好友失败";
  public final static String FRIENDER_PASS="通过好友请求失败";
  public final static String SUCCESS_PASS="通过好友请求成功";
  public final static String FRIENDER_REFUSE="拒绝好友请求失败";
  public final static String INFO_SAVE_SUCCESS="数据保存成功";
  public final static String INFO_NO_EXIST="数据不存在";
  public final static String INFO_NO_LOGIN="没有登陆";
  public final static String SUCCESS_PUBLISH="发布成功";
}
