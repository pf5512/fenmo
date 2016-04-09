package com.cn.fenmo.test;

import com.cn.fenmo.disruptor.BoatEvent;
import com.cn.fenmo.disruptor.BoatEventHandler;
import com.cn.fenmo.gt.PushServiceImp;
import com.cn.fenmo.gt.client.PushClient;
import com.cn.fenmo.gt.push.PushObject;

/**
 * Created by liuyin on 16/3/29.
 */
public class PushEventHandler extends BoatEventHandler {
    @Override
    public void onEvent(BoatEvent eventBoat, long l, boolean b) {
        try{
            Object object = eventBoat.getGoods();
            if (null == object || !(object instanceof PushObject)) {
              return;
            }
            PushClient pushClient = new PushServiceImp();
            PushObject ob  = (PushObject)object;
            pushClient.pushMessageByList(ob.getTitle(), ob.getMessage(), ob.getTransmissionMessage(),ob.getPushList(), ob.getPushType());
            System.out.println(ob.toString());
        }catch (Exception e){
            System.out.println(" PushToPostManEventHandler 线程 出现bug 请及时修复 ！！！！！");
        }


    }
}
