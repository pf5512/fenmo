package com.cn.fenmo.file;

import java.util.Properties;


public class NginxUtil {
  
  public static String getNginxDisk(){
    ParseXML pasexml = new ParseXML();
    pasexml.parse("nginx.xml");
    Properties properties = pasexml.getProps();
    String disk = properties.getProperty("nginxdisk");
    return disk;
  }
  public static String getNginxIP(){
    ParseXML pasexml = new ParseXML();
    pasexml.parse("nginx.xml");
    Properties properties = pasexml.getProps();
    String ip = properties.getProperty("ip");
    return ip;
  }

}
