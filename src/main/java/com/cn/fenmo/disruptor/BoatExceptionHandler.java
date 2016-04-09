package com.cn.fenmo.disruptor;

import com.lmax.disruptor.ExceptionHandler;

/**
 * Created by liuyin on 15/11/17.
 */
public abstract class BoatExceptionHandler implements ExceptionHandler<BoatEvent> {
    private  String  exceptionHandlerName;

    public String getExceptionHandlerName() {
        return exceptionHandlerName;
    }

    public void setExceptionHandlerName(String exceptionHandlerName) {
        this.exceptionHandlerName = exceptionHandlerName;
    }

}
