package com.cn.fenmo.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Administrator on 2015-08-27.
 */
public class BoatEventFactory implements EventFactory<BoatEvent> {
    public BoatEvent newInstance() {
        return new BoatEvent();
    }
}
