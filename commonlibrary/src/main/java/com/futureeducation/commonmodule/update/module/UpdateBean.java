package com.futureeducation.commonmodule.update.module;

import androidx.annotation.Keep;

@Keep
public class UpdateBean {


    /*
    * CREATE TABLE `version_upgrade` (
    `id` smallint(4) unsigned NOT NULL AUTO_INCREMENT,
    `app_id` smallint(4) unsigned NOT NULL DEFAULT '0' COMMENT 'app id 1未来教育学生，2未来教育教师',
    `app_name` varchar(20)  NOT NULL COMMENT 'app名称',
    `version_id` smallint(4) unsigned DEFAULT '0' COMMENT '程序比较版本号',
    `version_code` varchar(10) DEFAULT NULL COMMENT '版本标识 1.2',
    `type` tinyint(2) unsigned DEFAULT NULL COMMENT '是否升级 1升级，2强制升级',
    `app_url` varchar(255) DEFAULT NULL COMMENT'app 更新URL',
    `app_md5` varchar(255) DEFAULT NULL COMMENT'app md5',
    `upgrade_point` varchar(255) DEFAULT NULL COMMENT '升级提示',
    `isdel` tinyint(1) DEFAULT NULL,
    `cdate` date NOT NULL,
    `udate` DATE ,
    `ddate` DATE ,
    PRIMARY KEY (`id`)
    * */
    private int id;
    private int app_id;
    private String app_name;
    private int version_id;//程序比较版本号
    private String version_code;
    private int type;//是否升级 1升级，2强制升级',
    private String app_url;
    private String app_md5;
    private String upgrade_point;
    private int isdel;
    private String cdate;
    private String udate;
    private String ddate;

    public UpdateBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getApp_md5() {
        return app_md5;
    }

    public void setApp_md5(String app_md5) {
        this.app_md5 = app_md5;
    }

    public String getUpgrade_point() {
        return upgrade_point;
    }

    public void setUpgrade_point(String upgrade_point) {
        this.upgrade_point = upgrade_point;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "id=" + id +
                ", app_id=" + app_id +
                ", app_name='" + app_name + '\'' +
                ", version_id=" + version_id +
                ", version_code='" + version_code + '\'' +
                ", type=" + type +
                ", app_url='" + app_url + '\'' +
                ", app_md5='" + app_md5 + '\'' +
                ", upgrade_point='" + upgrade_point + '\'' +
                ", isdel=" + isdel +
                ", cdate='" + cdate + '\'' +
                ", udate='" + udate + '\'' +
                ", ddate='" + ddate + '\'' +
                '}';
    }
}
