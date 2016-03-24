package com.easemob.server.httpclient.api;

import java.net.URL;

import com.easemob.server.httpclient.vo.ClientSecretCredential;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easemob.server.comm.Constants;
import com.easemob.server.comm.HTTPMethod;
import com.easemob.server.comm.Roles;
import com.easemob.server.httpclient.utils.HTTPClientUtils;
import com.easemob.server.httpclient.vo.Credential;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * REST API Demo : ������Ϣ����REST API HttpClient4.3ʵ��
 * 
 * Doc URL: https://docs.easemob.com/doku.php?id=start:100serverintegration:30chatlog
 * 
 * @author Lynch 2014-09-15
 * 
 */
public class EasemobChatMessage {

 private static final Logger LOGGER = LoggerFactory.getLogger(EasemobChatMessage.class);
 private static final JsonNodeFactory factory = new JsonNodeFactory(false);
 private static final String APPKEY = Constants.APPKEY;

    // ͨ��app��client_id��client_secret����ȡapp����Աtoken
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);
   

    /**
  * ��ȡ������Ϣ
  * 
  * @param queryStrNode
  *
  */
   public static ObjectNode getChatMessages(ObjectNode queryStrNode) {
     ObjectNode objectNode = factory.objectNode();
     // check appKey format
     if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
       LOGGER.error("Bad format of Appkey: " + APPKEY);
       objectNode.put("message", "Bad format of Appkey");
       return objectNode;
     }
     try {
      String rest = "";
      if (null != queryStrNode && queryStrNode.get("ql") != null && !StringUtils.isEmpty(queryStrNode.get("ql").asText())) {
       rest = "ql="+ java.net.URLEncoder.encode(queryStrNode.get("ql").asText(), "utf-8");
      }
      if (null != queryStrNode && queryStrNode.get("limit") != null && !StringUtils.isEmpty(queryStrNode.get("limit").asText())) {
       rest = rest + "&limit=" + queryStrNode.get("limit").asText();
      }
      if (null != queryStrNode && queryStrNode.get("cursor") != null && !StringUtils.isEmpty(queryStrNode.get("cursor").asText())) {
       rest = rest + "&cursor=" + queryStrNode.get("cursor").asText();
      }
    
      URL chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatmessages?" + rest);
      objectNode = HTTPClientUtils.sendHTTPRequest(chatMessagesUrl, credential, null, HTTPMethod.METHOD_GET);
     } catch (Exception e) {
       e.printStackTrace();
     }
     return objectNode;
   }
   public static void main(String[] args) {
     // ������Ϣ ��ȡ���µ�20����¼
     ObjectNode queryStrNode = factory.objectNode();
     queryStrNode.put("ql", "select+*+where+from='mm1'+and+to='mm2'");
     queryStrNode.put("limit", "20");
     ObjectNode messages = getChatMessages(queryStrNode);
     // ������Ϣ ��ȡ7�����ڵ���Ϣ
     String currentTimestamp = String.valueOf(System.currentTimeMillis());
     String senvenDayAgo = String.valueOf(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
     ObjectNode queryStrNode1 = factory.objectNode();
     queryStrNode1.put("ql", "select * where timestamp>" + senvenDayAgo + " and timestamp<" + currentTimestamp);
     ObjectNode messages1 = getChatMessages(queryStrNode1);
     /*// ������Ϣ ��ҳ��ȡ
     ObjectNode queryStrNode2 = factory.objectNode();
     queryStrNode2.put("limit", "20");
     // ��һҳ
     ObjectNode messages2 = getChatMessages(queryStrNode2);
     // �ڶ�ҳ
     String cursor = messages2.get("cursor").asText();
     queryStrNode2.put("cursor", cursor);
     ObjectNode messages3 = getChatMessages(queryStrNode2);*/
  }
}
