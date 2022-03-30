package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

/**
 * Created by ResponseBean.
 * User: ASUS
 * Date: 2019/12/23
 * Time: 20:29
 */
@Keep
public class ResponseBean<T> {


    private int statesCode;
    private String message;
    private String accessToken;
    private T data;

    public ResponseBean() {
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "statesCode=" + statesCode +
                ", message='" + message + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", data=" + data +
                '}';
    }
}
