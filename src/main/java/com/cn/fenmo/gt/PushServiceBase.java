package com.cn.fenmo.gt;

import com.cn.fenmo.gt.client.Result;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import java.io.*;


public class PushServiceBase {
    static String appId = "aN0Jz7eCyGAiG5NP9OeDU6";
    static String appkey = "hzkc4DZnDH8dAqH3IdSAb6";
    static String master = "QFw09LOtAg8pTnDFTFj9p4";
    static String appSecret = "3PxoF6ezq079W3SjjK0mZ8";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    IGtPush push = new IGtPush(host, appkey, master);
    public static Result getResultFromStr(String str) {
        if (str == null || "".equals(str)) {
            return new Result(Result.FAILURE);
        } else if (str.contains(Result.FAILURE + "")) {
            return new Result(Result.FAILURE);
        } else if (str.contains(Result.SUCCESS + "")) {
            return new Result(Result.SUCCESS);
        } else if (str.contains(Result.PHONE_NUM_NOT_RIGHT + "")) {
            return new Result(Result.PHONE_NUM_NOT_RIGHT);
        } else {
            return new Result(Result.FAILURE);
        }
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 获取 点击通知 启动应用模板
     *
     * @return
     */
    public static NotificationTemplate notificationTemplateDemo(String title, String content, String transmissionString) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(content);
        // 配置通知栏图标
        template.setLogo("icon.png");
        // 配置通知栏网络图标
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        //TODO 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(transmissionString);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }

    /**
     * 获取 点击通知 打开链接模板
     *
     * @return
     */
    public static LinkTemplate linkTemplateDemo(String title, String message, String url) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(message);
        // 配置通知栏图标
        template.setLogo("icon.png");
        // 配置通知栏网络图标，填写图标URL地址
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
        template.setUrl(url);
        return template;
    }

    protected ListMessage getListMessage(AbstractTemplate template) {
        ListMessage listMessage = new ListMessage();
        listMessage.setData(template);

        //设置消息离线，并设置离线时间
        listMessage.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        listMessage.setOfflineExpireTime(24 * 1000 * 3600);
        return listMessage;
    }

    /**
     * 单推消息的消息体
     *
     * @return
     */
    protected SingleMessage getSingleMessage(AbstractTemplate template) {
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        return message;
    }

    /**
     * 获取透传消息模板
     *
     * @return
     */
    public TransmissionTemplate TransmissionTemplateDemo(String transmissionString) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        //TODO 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动。
        template.setTransmissionType(2);
        template.setTransmissionContent(transmissionString);
        return template;
    }
}
