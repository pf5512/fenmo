package com.cn.fenmo.disruptor;

/**
 * Created by Administrator on 2015-08-27.
 */
public class BoatEvent<Goods> {
    private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
