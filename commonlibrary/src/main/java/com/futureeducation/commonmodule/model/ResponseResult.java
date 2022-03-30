package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * Created by ${zhou}.
 * User: Administrator
 * Date: 2019/4/30
 * Time: 15:23
 * 相应数据
 */

@Keep
public class ResponseResult<T> {

    /**
     * code : 5
     * message : 此用户不存在或者未激活
     */

    @JSONField(name = "code")
    private int code;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "data")
    private T data;
    @JSONField(name = "success")
    private boolean mSucceed;

    public boolean ismSucceed() {
        return mSucceed;
    }

    public void setSucceed(boolean mSucceed) {
        this.mSucceed = mSucceed;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", mSucceed=" + mSucceed +
                '}';
    }
}

