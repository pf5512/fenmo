package com.cn.fenmo.util;

public final class CNST {
  
  public final static int VSTATE_WORK             = 0;
  public final static int VSTATE_CANCEL           = 1;
  public final static String VSTATE_WORK_STR      = "有效";
  public final static String VSTATE_CANCEL_STR    = "无效";
  
  public final static int CIRCLE_YL          = 1;
  public final static int CIRCLE_CJ          = 2;
  public final static int CIRCLE_FDC         = 3;
  public final static int CIRCLE_ZMT         = 4;
  public final static int CIRCLE_HLW         = 5;
  public final static String CIRCLE_YL_STR      = "娱乐";
  public final static String CIRCLE_CJ_STR      = "财经";
  public final static String CIRCLE_FDC_STR     = "房地产";
  public final static String CIRCLE_ZMT_STR     = "自媒体";
  public final static String CIRCLE_HLW_STR     = "自媒体";
  
  
  public final static int TOKEN_CANCEL=12*60*60*1000;//一天
  
  
  public static String getVStateStr(int vState){
    String result = "";
    switch(vState){
      case VSTATE_WORK:{
        result= VSTATE_WORK_STR;
        break;
      }
      case VSTATE_CANCEL:{
        result= VSTATE_CANCEL_STR;
        break;
      }
    }
    return result;
  }
  
  public static String getCircleStr(int circle){
    String result = "";
    switch(circle){
      case CIRCLE_YL:{
        result = CIRCLE_YL_STR;
        break;
      }
      case CIRCLE_CJ:{
        result= CIRCLE_CJ_STR;
        break;
      }
      case CIRCLE_FDC:{
        result= CIRCLE_FDC_STR;
        break;
      }
      case CIRCLE_ZMT:{
        result= CIRCLE_ZMT_STR;
        break;
      }
    }
    return result;
  }
}
