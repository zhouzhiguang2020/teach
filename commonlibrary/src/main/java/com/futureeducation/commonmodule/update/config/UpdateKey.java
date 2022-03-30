package com.futureeducation.commonmodule.update.config;

/**
 * check
 */
public class UpdateKey {
    public static String API_TOKEN = "";
    public static String APP_ID = "";
    //APK 检查地址
    public static String CHECK_APP_URL = "";
    //default
    public final static int WITH_DEFAULT = 0;//默认后台下载
    public final static int WITH_DIALOG = 1;//dilaog 显示下载
    public final static int WITH_NOTIFITION = 2;//通知栏显示下载
    //notifyId
    public final static int NOTIFICATIONID = 115;
    public static int DialogOrNotification = -1;//default 默认方式 3是跳转到连接跟新 0是后台更新下载

}
