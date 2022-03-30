package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by RequestResult.
 * User: ASUS
 * Date: 2019/11/12
 * Time: 15:03
 * 解析的modle类
 */
@Keep
public class RequestResult<T> {



    private int statesCode;
    private String message;
    @JSONField(name = "data")
    private T data;

    public RequestResult() {
    }

    public int getStatesCode() {
        return statesCode;
    }

    public void setStatesCode(int statesCode) {
        this.statesCode = statesCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestResult{" +
                "statesCode=" + statesCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
