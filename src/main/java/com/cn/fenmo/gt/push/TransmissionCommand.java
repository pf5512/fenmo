package com.cn.fenmo.gt.push;

import com.google.gson.Gson;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class TransmissionCommand<T> implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * 推送代号，定义需要进行什么操作表示等。
     */
    private int command;
    /**
     * 数据  透传消息需要携带的数据。
     */
    private T data;

    public static TransmissionCommand fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(TransmissionCommand.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(TransmissionCommand.class, clazz);
        return gson.toJson(this, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
