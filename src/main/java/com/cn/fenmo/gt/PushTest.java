package com.cn.fenmo.gt;
import java.util.Arrays;
import com.cn.fenmo.gt.client.PushClient;
import com.cn.fenmo.gt.client.Status;
import com.cn.fenmo.gt.push.TransmissionCommand;

/**
 * Created by liuyin on 16/3/16.
 */
public class PushTest {

    public static void main(String a[]) {

        PushServiceImp pushServiceImp = new PushServiceImp();

        //推送透传消息，没有提示的，只有APP后台才有相应的
        TransmissionCommand<String> transmissionCommand = new TransmissionCommand<String>();
        transmissionCommand.setCommand(Status.PushCommand.ADD_FRIEND);
        transmissionCommand.setData("这里写透传消息需要携带的数据。");
        pushServiceImp.pushTransmissionMessageByList(
                transmissionCommand.toJson(String.class),
                Arrays.asList("18680683004"), //这里写需要接受通知的ALias标签的用户
                PushClient.PUSH_BY_ALIAS);//这里指定推送识别接受者的方式。
        /**
         * 推送支持如下三种方式，CID表示根据设备号推送，TAGS 表示根据TAG推送，ALIAS用户标签推送，一个用户可以有多个TAG，只能有一个ALIAS，和CID。
         */

        //有通知的推送提示。
        pushServiceImp.pushMessageByList(
                "这里写推送消息的标题",
                "这里写推送消息显示的信息。",
                "这里写需要透传的消息，不会显示，如果不需要携带透传消息，可以不写",
                Arrays.asList("18680683004")//这里写需要接受通知的ALias标签的用户
                , PushClient.PUSH_BY_ALIAS);//这里指定推送识别接受者的方式。


        //根据tag推送
        pushServiceImp.pushMessageToAppByTag( "这里写推送消息的标题",
                "这里写推送消息显示的信息。",
                "这里写需要透传的消息，不会显示，如果不需要携带透传消息，可以不写",
                Arrays.asList("18680683004"));//最后一个参数是TAG List.



    }
}
