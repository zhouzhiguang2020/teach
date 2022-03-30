package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

/**
 * 微信登录实
 */
@Keep
public class WXTokenBean {

    private String userToken;
    private String timespan;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }
}
