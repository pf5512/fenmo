package com.cn.fenmo.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/8/27.
 */
public class BoatDisruptor<T> {
    private Disruptor<BoatEvent> disruptor;
    private BoatEventProducer producer;
    final int bufferSize = 1024 * 1024;

    public BoatDisruptor() {
        Executor executor = Executors.newCachedThreadPool();
        BoatEventFactory boatEventFactory = new BoatEventFactory();
        this.disruptor = new Disruptor<BoatEvent>(boatEventFactory, bufferSize, executor);
        RingBuffer<BoatEvent> ringBuffer = disruptor.getRingBuffer();
        this.producer = new BoatEventProducer(ringBuffer);
    }

    public void start() {
        disruptor.start();
    }

    public void shutdown() {
        disruptor.shutdown();
    }

    public void ondata(T t) {
        this.producer.onData(t);
    }

    public Disruptor<BoatEvent> getDisruptor() {
        return disruptor;
    }

    public void setDisruptor(Disruptor<BoatEvent> disruptor) {
        this.disruptor = disruptor;
    }

    public BoatEventProducer getProducer() {
        return producer;
    }

    public void setProducer(BoatEventProducer producer) {
        this.producer = producer;
    }
}
