package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

import java.util.List;

/**
 * Created by .
 * User: ASUS
 * Date: 2020/9/15
 * Time: 15:28
 */
@Keep
public class ResponseListBean<T> {


    private int code;
    private String message;
    private List<T> data;

    public ResponseListBean() {
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseListBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
