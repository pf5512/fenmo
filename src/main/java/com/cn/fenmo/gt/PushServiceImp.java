package com.cn.fenmo.gt;

import com.cn.fenmo.gt.client.PushClient;
import com.cn.fenmo.gt.push.PushObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("pushClient")
public class PushServiceImp extends PushServiceBase implements PushClient {
    public void push(PushObject object) {
        try {
            if (object == null || object.getListType() == 0) {
                return ;
            }
            if (object.getPushType() == PushClient.PUSH_TYPE_NOTIFICATION) {
                //获取推送模板
                NotificationTemplate template = notificationTemplateDemo(object.getTitle(), object.getMessage(), object.getTransmissionMessage());
                pushMessage(object.getPushList(), object.getListType(), template);
            } else if (object.getPushType() == PushClient.PUSH_TYPE_TRANSMISSION) {
                //获取推送模板
                TransmissionTemplate template = TransmissionTemplateDemo(object.getMessage());
                pushMessage(object.getPushList(), object.getListType(), template);
            } else if (object.getPushType() == PushClient.PUSH_TYPE_TO_ALL) {
                AbstractTemplate template = notificationTemplateDemo(object.getTitle(), object.getMessage(), object.getTransmissionMessage());
                AppMessage message = new AppMessage();
                message.setData(template);
                //设置消息离线，并设置离线时间
                message.setOffline(true);
                //离线有效时间，单位为毫秒，可选
                message.setOfflineExpireTime(24 * 1000 * 3600);
                //设置推送目标条件过滤
                List appIdList = new ArrayList();
                appIdList.add(appId);
                message.setAppIdList(appIdList);
                message.setTagList(object.getPushList());
                IPushResult ret = push.pushMessageToApp(message);
                System.out.println(ret.toString());
            }
        } catch (
                Exception e
                )

        {
            e.printStackTrace();

        }
    }



    /**
     * 测试接口
     *
     * @param testStr
     * @return
     */
    public String interfaceTest(String testStr) {
        return "PushServiceImp test ok !";
    }
    public Boolean pushMessageByList(String title, String message, String transmissionMessage, List<String> pushList, int listType) {
        if (pushList == null || pushList.size() == 0) {
            return false;
        }
        PushObject pushObject = new PushObject(title, message, transmissionMessage, pushList, listType, PushClient.PUSH_TYPE_NOTIFICATION);
        push(pushObject);
        return true;

    }

    public Boolean pushTransmissionMessageByList(String message, List<String> pushList, int listType) {
        if (pushList == null || pushList.size() == 0) {
            return false;
        }
        PushObject pushObject = new PushObject("", message, "", pushList, listType, PushClient.PUSH_TYPE_TRANSMISSION);
        push(pushObject);
        return true;

    }


    public Boolean pushMessageToAppByTag(String title, String stringMessage, String transmissionMessage, List<String> tagList) {
        if (tagList == null || tagList.size() == 0) {
            return false;
        }
        PushObject pushObject = new PushObject(title, stringMessage, transmissionMessage, tagList, PUSH_BY_TAGS, PushClient.PUSH_TYPE_TRANSMISSION);
        push(pushObject);
        return true;
    }
    /**
     * 推送消息
     *
     * @param pushList
     * @param listType
     * @param template
     * @return
     */
    private String pushMessage(List<String> pushList, int listType, AbstractTemplate template) {
        if (pushList.size() == 1) {
            //单推
            //获取推送模板
            SingleMessage singleMessage = getSingleMessage(template);
            //获取推送taget
            Target target = new Target();
            target.setAppId(appId);
            switch (listType) {
                case PUSH_BY_ALIAS:
                    target.setAlias(pushList.get(0));
                    break;
                case PUSH_BY_CID:
                    target.setClientId(pushList.get(0));
                    break;
                default:
                    return PUSH_FAILURE;
            }
            return pushSingleMessage(singleMessage, target);

        } else {
            //群推
            //获取list推送消息
            //配置推送目标
            List targets = new ArrayList();
            for (String s : pushList) {
                Target target = new Target();
                target.setAppId(appId);
                switch (listType) {
                    case PUSH_BY_ALIAS:
                        target.setAlias(s);
                        break;
                    case PUSH_BY_CID:
                        target.setClientId(s);
                        break;
                    default:
                        return PUSH_FAILURE;
                }
                targets.add(target);

            }
            ListMessage listMessage = getListMessage(template);
            //获取taskID
            String taskId = push.getContentId(listMessage);
            //使用taskID对目标进行推送
            IPushResult ret = push.pushMessageToList(taskId, targets);
            //打印服务器返回信息
            System.out.println(
                    " ret.getResponse() " + ret.getResponse().toString()
            );
            return ret.getResponse().toString();
        }
    }

    private String pushSingleMessage(SingleMessage singleMessage, Target target) {
        String result;
        IPushResult ret = null;

        try {

            ret = push.pushMessageToSingle(singleMessage, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(singleMessage, target, e.getRequestId());
        }

        if (ret != null) {
            result = ret.getResponse().toString();
        } else {
            result = PUSH_FAILURE;
        }
        System.out.println("  pushMessageToSingle  = " + result
                + " target getClientId " + target.getClientId()
                + " target getAlias " + target.getAlias());

        return result;
    }


}