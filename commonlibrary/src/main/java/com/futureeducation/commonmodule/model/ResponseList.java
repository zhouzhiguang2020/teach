package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

import java.util.List;

/**
 * Created by ResponseList.
 * User: ASUS
 * Date: 2019/12/27
 * Time: 14:46
 */
@Keep
public class ResponseList<T> {

    private int statesCode;
    private String message;
    private List<T> data;

    public ResponseList() {
    }

    @Override
    public String toString() {
        return "ResponseList{" +
                "statesCode=" + statesCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
