package com.cn.fenmo.test;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by liuyin on 16/3/29.
 */
public class PushBean implements Serializable {


    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public PushBean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PushBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
