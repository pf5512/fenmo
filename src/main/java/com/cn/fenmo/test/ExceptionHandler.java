package com.cn.fenmo.test;

import com.cn.fenmo.disruptor.BoatEvent;
import com.cn.fenmo.disruptor.BoatExceptionHandler;
/**
 * Created by liuyin on 16/3/29.
 */
public class ExceptionHandler extends BoatExceptionHandler {

    public void handleEventException(Throwable throwable, long l, BoatEvent boatEvent) {
        System.out.println(" 处理事件异常");
    }

    public void handleOnStartException(Throwable throwable) {
        System.out.println(" 处理启动异常");

    }

    public void handleOnShutdownException(Throwable throwable) {
        System.out.println(" 处理关闭异常");

    }
}
