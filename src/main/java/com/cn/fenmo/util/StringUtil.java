package com.cn.fenmo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.cn.fenmo.pojo.UserBean;
import com.cn.fenmo.redis.RedisClient;

public class StringUtil extends StringUtils {

  public static boolean isValidPhone(String phone){
    if(!StringUtil.isNumeric(phone)){
      return false;
    };
    if(trim(phone).length()!=11){
      return false;
    }
    return true;
  }
 
  /**
  * @param regex
  * 正则表达式字符串
  * @param str
  * 要匹配的字符串
  * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
  */
  private static boolean match(String regex, String str) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);
    return matcher.matches();
  }
  public static boolean isNumeric(String str){
    if(StringUtil.isNull(str)){
       return false;
    }
    for(int i=str.length();--i>=0;){
       int chr=str.charAt(i);
       if(chr<48 || chr>57)
          return false;
    }
    return true;
  }

  public static boolean isNull(String data) {
    return (data == null || data.trim().length() < 1);
  }
  
  public static boolean isNull(Object data) {
    return (data == null || data.toString().trim().length() < 1);
  }


  public static boolean isNotNull(String data) {
    return !isNull(data);
  }
  
  public static boolean isNotNull(Object data) {
    return !isNull(data);
  }

  public static String toNull(String data) {
    return isNull(data) ? null : data;
  }

  public static String toString(String data) {
    return isNull(data) ? "" : data;
  }
  
  public static String toString(Object data) {
    return isNull(data) ? "" : data.toString();
  }

  public static String toString(BigDecimal data) {
    return data == null ? "" : data.toString();
  }

  public static BigDecimal NulltoZero(String data) {
    BigDecimal rtData = new BigDecimal(0);
    if (!isNull(data)) {
      rtData = new BigDecimal(data);
    }
    return rtData;
  }
  
  public static BigDecimal toNumber(String data) {
    BigDecimal rtData = new BigDecimal(0);
    if (!isNull(data)) {
      rtData = new BigDecimal(data);
    }
    return rtData;
  }

  public static BigDecimal NulltoZero(BigDecimal data) {
    BigDecimal rtData = new BigDecimal(0);
    if (data != null) {
      rtData = data;
    }
    return rtData;
  }
  
  public static BigDecimal NulltoZero(Object data) {
    BigDecimal rtData = new BigDecimal(0);
    if (!isNull(data)) {
      rtData = new BigDecimal(data.toString());
    }
    return rtData;
  }

  /**
   * 生成随机字符串
   * 
   * @param size
   * @return
   */
  public static String getRandomString(int size) {
    char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
        'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < size; i++) {
      sb.append(c[Math.abs(random.nextInt()) % c.length]);
    }
    return sb.toString();
  }

  /**
   * 格式化数字
   * 
   * @param str
   * @return
   */
  public static String toNumberFormat(String str) {
    NumberFormat n = NumberFormat.getNumberInstance();
    n.setGroupingUsed(true);
    double d;
    String outStr = null;
    try {
      d = Double.parseDouble(str);
      outStr = n.format(d);
    } catch (Exception e) {
    }
    return outStr;
  }

  /**
   * 格式化数字
   * 
   * @param str
   * @return
   */
  public static String toDecimalFormat(String str) {
    return toDecimalFormat(str, "##,###,###,###,##0.00000");
  }

  /**
   * 格式化数字
   * 
   * @param str
   * @param pattern
   * @return
   */
  public static String toDecimalFormat(String str, String pattern) {
    if (StringUtil.isEmpty(pattern)) {
      return str;
    }
    DecimalFormat fmt = new DecimalFormat(pattern);
    fmt.setGroupingUsed(true);
    String outStr = null;
    double d;
    try {
      d = Double.parseDouble(str);
      outStr = fmt.format(d);
    } catch (Exception e) {
    }
    return outStr;
  }

  /**
   * 格式化金额
   * 
   * @param str
   * @return
   */
  public static String toCurrencyFormat(String str) {
    NumberFormat n = NumberFormat.getCurrencyInstance(Locale.CHINA);
    n.setGroupingUsed(true);
    double d;
    String outStr = null;
    try {
      d = Double.parseDouble(str);
      outStr = n.format(d);
    } catch (Exception e) {
    }
    return outStr;
  }

  /**
   * 格式化百分比
   * 
   * @param str
   * @return
   */
  public static String toPercentFormat(String str) {
    NumberFormat n = NumberFormat.getPercentInstance();
    n.setGroupingUsed(true);
    n.setMinimumFractionDigits(2);
    n.setMinimumIntegerDigits(1);
    double d;
    String outStr = null;
    try {
      d = Double.parseDouble(str);
      outStr = n.format(d);
    } catch (Exception e) {
    }
    return outStr;
  }

  /**
   * 转大小写模式为大写下划线模式(非首大写转下划线，全大写)
   * @param source 不带下划线的参数
   * @return
   */
  public static String unCamelUpperCase(String source) {
    if (StringUtil.isEmpty(source)) {
      return "";
    }
    String[] parts = split(source, "_");
    StringBuilder sb = new StringBuilder();
    for (String part : parts) {
      sb.append(capitalize(part));
    }
    source = capitalize(sb.toString());
    Pattern p = Pattern.compile("([A-Z]?[a-z0-9]*)");
    Matcher m = p.matcher(source);
    sb = new StringBuilder();
    while (m.find()) {
      sb.append(upperCase(m.group())).append("_");
    }
    return substringBefore(sb.toString(), "__");
  }

  /**
   * 转大小写模式为小写下划线模式(非首大写转下划线，全小写)
   * @param source
   * @return
   */
  public static String unCamelLowerCase(String source) {
    if (StringUtil.isEmpty(source)) {
      return "";
    }
    String[] parts = split(source, "_");
    StringBuilder sb = new StringBuilder();
    for (String part : parts) {
      sb.append(capitalize(part));
    }
    source = capitalize(sb.toString());
    Pattern p = Pattern.compile("([A-Z]?[a-z0-9]*)");
    Matcher m = p.matcher(source);
    sb = new StringBuilder();
    while (m.find()) {
      sb.append(lowerCase(m.group())).append("_");
    }
    return substringBefore(sb.toString(), "__");
  }

  /**
   * 转下划线模式为大小写模式(大写开头)(下划线后第一个字母改为大写，首字母大写其余全小写)
   * @param source
   * @return
   */
  public static String camelUpperCase(String source) {
    if (StringUtil.isEmpty(source)) {
      return "";
    }
    String[] parts = split(source, "_");
    StringBuilder sb = new StringBuilder();
    for (String part : parts) {
      sb.append(capitalize(lowerCase(part)));
    }
    return sb.toString();
  }
  /**
   * 转下划线模式为大小写模式(大写开头)(下划线后第一个字母改为大写，首字母大写其余全小写)
   * @param source
   * @return
   */
  public static String HeadUpperCase(String source) {
    if (StringUtil.isEmpty(source)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append(upperCase(source.substring(0,1))).append(source.substring(1));
    return sb.toString();
  }
  
  /**
   * 转成大写字符
   * @param source
   * @return
   */
  public static String UpperCase(String source) {
    if (StringUtil.isEmpty(source)) {
      return "";
    }
    
    return source.toUpperCase();
  }
  
  /**
   * 转成小写字符
   * @param source
   * @return
   */
  public static String LowerCase(String source) {
    if (StringUtil.isEmpty(source)) {
      return "";
    }
    return source.toLowerCase();
  }

  /**
   * 转下划线模式为大小写模式(小写开头)(下划线后第一个字母改为大写，其余全小写)
   * @param source
   * @return
   */
  public static String camelLowerCase(String source) {
    if (StringUtil.isEmpty(source)) {
      return "";
    }
    String[] parts = split(source, "_");
    StringBuilder sb = new StringBuilder();
    for (String part : parts) {
      sb.append(capitalize(lowerCase(part)));
    }
    return uncapitalize(sb.toString());
  }

  public static String fixPath(String path) {
    String tempPath = path;
    if (isNotEmpty(tempPath)) {
      tempPath = StringUtil.replace(tempPath, "\\", "/");
      tempPath = StringUtil.endsWithIgnoreCase(tempPath, "/") ? tempPath : (tempPath + "/");
    }
    return tempPath;
  }
  
  /**
   * Xml字符转换
   * @param text
   * @return
   */
  public static String transFromXmlStr(String text) {
    if (text == null)
      return "";
    String tmp = text.replace("&rt;", ">");
    tmp = tmp.replace("&quot;", "\"");
    tmp = tmp.replace("&lt;", "<");
    tmp = tmp.replace("&#13;", "\r");
    tmp = tmp.replace("&#10;", "\n");
    tmp = tmp.replace("&amp;", "&");
    return tmp;
  }
  public static boolean isContainsChinese(String str){
    String regEx = "[\u4e00-\u9fa5]";
    Pattern pat = Pattern.compile(regEx);
    Matcher matcher = pat.matcher(str);
    boolean flg = false;
    if (matcher.find())    {
       flg = true;
    }
    return flg;
  }

  public static boolean isMobileNO(String mobiles){ 
    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0,6-8]))\\d{8}$"); 
    Matcher m = p.matcher(mobiles);  
    return m.matches();  
  }
  /*验证密码**/
  public static boolean isPassword(String str) {
    String regex = "^[0-9a-zA-Z!@*&#$]{6,18}$";
    return match(regex, str);
  }
  
  public static String getFmNO() {
    String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
    String fmNumString = "";
    for(int i=0;i<8;i++){
      fmNumString=fmNumString+chars.charAt((int)(Math.random() * 36));
    }
    return fmNumString;
  }
  
  public static final  Pattern PATTERN   =   Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);  
  public static String getImgStr(String htmlStr) {
    String imrUrl = "";
    if(StringUtil.isNull(htmlStr)){
      return "";
    }
    Matcher matcher = PATTERN.matcher(htmlStr);  
    List<String> list = new ArrayList<String>();  
    while(matcher.find()){  
        String   group   =   matcher.group(1);  
        if(group   ==   null)   {  
          continue;  
        }  
        if(group.startsWith("'")){  
          list.add(group.substring(1,group.indexOf("'",1)));  
        }else if (group.startsWith("\"")){  
          list.add(group.substring(1,group.indexOf("\"",1)));  
        }else{  
          list.add(group.split("\\s")[0]);  
        }  
    }  
    for(int i=0;i<list.size();i++){
      if(list.size()==1){
        imrUrl=list.get(i);
      }else{
        imrUrl=imrUrl+list.get(i)+"$";
      }
     
    }
    return imrUrl;   
  }
  /**
   * 测试
   * 
   * @param args
   */
  volatile int a = 1;  
  volatile boolean ready;  
    
  public class PrintA extends Thread{  
      @Override  
      public void run() {  
          while(!ready){  
              Thread.yield();  
          }  
          System.out.println(ready);  
      }  
  }  
  
  public static void setRides(String userPhone,UserBean bean) {
//    RedisClient.set(userPhone, bean.getToken(), CNST.TOKEN_CANCEL);
//    RedisClient.setObject(bean.getToken(), bean, CNST.TOKEN_CANCEL);
  }
  public static void main(String[] args) throws InterruptedException {  
//      StringUtil t = new StringUtil();
//      t.new PrintA().start();  
//      //下面两行如果不加volatile的话，执行的先后顺序是不可预测的。并且下面两行都是原子操作，但是这两行作为一个整体的话就不是一个原子操作。  
//      t.a = 48; //这是一个原子操作，但是其结果不一定具有可见性。加上volatile后就具备了可见性。  
//      t.ready = true;//同理  
         String imgUrl = "17930234852";
         
         System.out.println(StringUtil.isMobileNO(imgUrl));
  } 
  
  

 

}

