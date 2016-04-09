package com.cn.fenmo.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by Administrator on 2015-08-27.
 */
public abstract class BoatEventHandler implements EventHandler<BoatEvent> {


    public String handlerName;

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public abstract void onEvent(BoatEvent eventBoat, long l, boolean b);
}
