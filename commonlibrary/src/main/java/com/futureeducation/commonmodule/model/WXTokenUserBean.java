package com.futureeducation.commonmodule.model;

import androidx.annotation.Keep;

/**
 * 微信登录实
 */
@Keep
public class WXTokenUserBean {

    private String per_id;
    private String per_name;
    private String per_userid;
    private String uni_id;
    private String roleCode;

    public String getPer_id() {
        return per_id;
    }

    public void setPer_id(String per_id) {
        this.per_id = per_id;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public String getPer_userid() {
        return per_userid;
    }

    public void setPer_userid(String per_userid) {
        this.per_userid = per_userid;
    }

    public String getUni_id() {
        return uni_id;
    }

    public void setUni_id(String uni_id) {
        this.uni_id = uni_id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
