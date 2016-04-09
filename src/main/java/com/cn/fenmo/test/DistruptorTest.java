package com.cn.fenmo.test;

import com.cn.fenmo.disruptor.BoatDisruptor;


/**
 * Created by liuyin on 16/3/29.
 */
public class DistruptorTest {
    public static  BoatDisruptor boatDisruptor = new BoatDisruptor();
    static{
        boatDisruptor.getDisruptor().handleEventsWith(new PushEventHandler());
        boatDisruptor.getDisruptor().handleExceptionsWith(new ExceptionHandler());
        boatDisruptor.getDisruptor().start();
    }

    public static void main(String a[]){
        for (;;){
            PushBean pushBean=new PushBean("我是推送消息");
            boatDisruptor.ondata(pushBean);
        }
    }
}
