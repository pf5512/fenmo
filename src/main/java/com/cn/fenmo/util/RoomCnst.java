package com.cn.fenmo.util;

public class RoomCnst {
  public final static int ROOM_TYPE_YL                   = 1;  //娱乐
  public final static int ROOM_TYPE_CJ                   = 2;  //财经
  public final static int ROOM_TYPE_FC                   = 3;  //房产
  public final static int ROOM_TYPE_ZMT                  = 4;  //自媒体
  public final static String ROOM_TYPE_YL_STR            = "娱乐";  //娱乐
  public final static String ROOM_TYPE_CJ_STR            = "财经";  //财经
  public final static String ROOM_TYPE_FC_STR            = "房产";  //房产
  public final static String ROOM_TYPE_ZMT_STR           ="自媒体";  //自媒体
  
  public final static int ROOM_PUBLIC                    = 1;  //公开群
  public final static int ROOM_PRIVATE                   = 0;  //私有群
  
  public final static String ROOM_PUBLIC_STR             = "公开";  //
  public final static String ROOM_NO_PUBLIC_STR          = "私有";  //
  
  public final static int ROOM_ALLOW_JOIN = 0 ;           //0 允许群成员邀请人加入此群
  public final static int ROOM_NO_ALLOW_JOIN = 1 ;        //1 只有群主才可以往群里加人
  
  public final static int ROOM_JOIN_NO_PS = 0 ;           //0 公开群不需要群主审批
  public final static int ROOM_JOIN_YES_PS_= 1 ;          //1 公开群需要群主审批
  
  
  public final static int ROOM_MEMBERONLY_YES = 0 ;        //0 只允许群成员发言
  public final static int ROOM_MEMBERONLY_NO  = 1 ;        //1 游客也可以发言
  public final static String ROOM_MEMBERONLY_YES_STR = "是" ;       
  public final static String ROOM_MEMBERONLY_NO_STR  = "否" ;        
  
  public static String getMemberonlyStr(int state){
    String result = "";
    switch(state){
      case ROOM_MEMBERONLY_YES:{
        result = ROOM_MEMBERONLY_YES_STR;
        break;
      }
      case ROOM_MEMBERONLY_NO:{
        result = ROOM_MEMBERONLY_NO_STR;
        break;
      }
    }
    return result;
  }
  
  public static String getTypeStr(int state){
    String result = "";
    switch(state){
      case ROOM_TYPE_YL:{
        result= ROOM_TYPE_YL_STR;
        break;
      }
      case ROOM_TYPE_CJ:{
        result= ROOM_TYPE_CJ_STR;
        break;
      }
      case ROOM_TYPE_FC:{
        result= ROOM_TYPE_FC_STR;
        break;
      }
      case ROOM_TYPE_ZMT:{
        result= ROOM_TYPE_ZMT_STR;
        break;
      }
    }
    return result;
  }
  
  public static String getIsPublicStr(int state){
    String result = "";
    switch(state){
      case ROOM_PUBLIC:{
        result= ROOM_PUBLIC_STR;
        break;
      }
      case ROOM_PRIVATE:{
        result= ROOM_NO_PUBLIC_STR;
        break;
      }
    }
    return result;
  }
}
