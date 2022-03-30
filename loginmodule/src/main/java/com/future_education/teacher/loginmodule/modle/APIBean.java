package com.future_education.teacher.loginmodule.modle;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @ClassName APIBean
 * @Description TODO
 * @Author ASUS
 * @Date 2020/5/12 11:11
 * @Version 1.0
 */
@Keep
public class APIBean implements Serializable {
    private String ApiUrl;

    public String getApiUrl() {
        return ApiUrl;
    }

    public void setApiUrl(String apiUrl) {
        ApiUrl = apiUrl;
    }


}
