package com.cn.fenmo.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * Created by Administrator on 2015-08-27.
 *
 * @
 */
public class BoatEventProducer<Goods> {

    private final RingBuffer<BoatEvent> ringBuffer;

    public BoatEventProducer(RingBuffer<BoatEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Goods goods) {
        long sequence = ringBuffer.next();
        try {
            BoatEvent event = ringBuffer.get(sequence);
            event.setGoods(goods);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
