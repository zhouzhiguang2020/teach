package com.futureeducation.commonmodule.http.converter;

/**
 * @ClassName ResponseBean
 * @Description TODO
 * @Author ASUS
 * @Date 2020/6/4 11:31
 * @Version 1.0
 */
public class ResponseBean {
    private String statesCode;
    private String message;

    public String getStatesCode() {
        return statesCode;
    }

    public void setStatesCode(String statesCode) {
        this.statesCode = statesCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "statesCode='" + statesCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
