package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

/**
 * 微信登录实
 */
@Keep
public class WXEntryBean {

    private String openid;
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
