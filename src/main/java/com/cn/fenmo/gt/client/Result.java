package com.cn.fenmo.gt.client;

import java.io.Serializable;
public class Result implements Serializable{
    public final static int SUCCESS = 200;
    public final static int FAILURE = 520;
    public final static int PHONE_NUM_NOT_RIGHT = 518;
    private int status;

    public Result() {

    }

    public Result(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
