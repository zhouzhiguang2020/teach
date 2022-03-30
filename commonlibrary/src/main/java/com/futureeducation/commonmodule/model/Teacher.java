package com.futureeducation.commonmodule.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Time: 14:36
 * 用户登录实体类 包含用户的登录一些登录的资料
 */
@Keep
public class Teacher implements Parcelable {


    @JSONField(name = "loginKey")
    private String loginKey;
    @JSONField(name = "token")
    private String token;//登录的token
    @JSONField(name = "userId")
    private String userId;//登录是userid

    public Teacher() {
    }


    @Override
    public String toString() {
        return "User{" +
                "loginKey='" + loginKey + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.loginKey);
        dest.writeString(this.token);
        dest.writeString(this.userId);
    }

    protected Teacher(Parcel in) {
        this.loginKey = in.readString();
        this.token = in.readString();
        this.userId = in.readString();
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel source) {
            return new Teacher(source);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
}
