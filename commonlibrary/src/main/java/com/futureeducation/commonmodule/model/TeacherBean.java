package com.futureeducation.commonmodule.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

/**
 * Time: 14:36
 * 用户登录实体类 包含用户的登录一些登录的资料
 */
@Keep
public class TeacherBean implements Parcelable {


    private String userAccount;
    private String userName;
    private String userId;
    private String userRole;
    private String unitId;
    private String unitName;
    private String provinceName;
    private int provinceId;
    private String areaName;
    private int areaId;
    private int cityId;
    private String cityName;
    private String token;
    private String unit_session_uid;
    private String per_pic;
    private int session_week;
    private boolean askUpdatePwd;

    public String getToken() {
        return token;
    }


    @Override
    public String toString() {
        return "TeacherBean{" +
                "userAccount='" + userAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", userRole='" + userRole + '\'' +
                ", unitId='" + unitId + '\'' +
                ", unitName='" + unitName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", provinceId=" + provinceId +
                ", areaName='" + areaName + '\'' +
                ", per_pic='" + per_pic + '\'' +
                ", areaId=" + areaId +
                ", cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", token='" + token + '\'' +
                ", unit_session_uid='" + unit_session_uid + '\'' +
                ", session_week='" + session_week + '\'' +
                '}';
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TeacherBean() {
    }

    public String getPer_pic() {
        return per_pic;
    }

    public void setPer_pic(String per_pic) {
        this.per_pic = per_pic;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnit_session_uid() {
        return unit_session_uid;
    }

    public void setUnit_session_uid(String unit_session_uid) {
        this.unit_session_uid = unit_session_uid;
    }

    public int getSession_week() {
        return session_week;
    }

    public void setSession_week(int session_week) {
        this.session_week = session_week;
    }

    public boolean isAskUpdatePwd() {
        return askUpdatePwd;
    }

    public void setAskUpdatePwd(boolean askUpdatePwd) {
        this.askUpdatePwd = askUpdatePwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userAccount);
        dest.writeString(this.userName);
        dest.writeString(this.userId);
        dest.writeString(this.userRole);
        dest.writeString(this.unitId);
        dest.writeString(this.unitName);
        dest.writeString(this.provinceName);
        dest.writeInt(this.provinceId);
        dest.writeString(this.areaName);
        dest.writeInt(this.areaId);
        dest.writeInt(this.cityId);
        dest.writeString(this.cityName);
        dest.writeString(this.token);
        dest.writeString(this.unit_session_uid);
        dest.writeString(this.per_pic);
        dest.writeInt(this.session_week);
        dest.writeByte(this.askUpdatePwd ? (byte) 1 : (byte) 0);
    }

    protected TeacherBean(Parcel in) {
        this.userAccount = in.readString();
        this.userName = in.readString();
        this.userId = in.readString();
        this.userRole = in.readString();
        this.unitId = in.readString();
        this.unitName = in.readString();
        this.provinceName = in.readString();
        this.provinceId = in.readInt();
        this.areaName = in.readString();
        this.areaId = in.readInt();
        this.cityId = in.readInt();
        this.cityName = in.readString();
        this.token = in.readString();
        this.unit_session_uid = in.readString();
        this.per_pic = in.readString();
        this.session_week = in.readInt();
        this.askUpdatePwd = in.readByte() != 0;
    }

    public static final Creator<TeacherBean> CREATOR = new Creator<TeacherBean>() {
        @Override
        public TeacherBean createFromParcel(Parcel source) {
            return new TeacherBean(source);
        }

        @Override
        public TeacherBean[] newArray(int size) {
            return new TeacherBean[size];
        }
    };
}
