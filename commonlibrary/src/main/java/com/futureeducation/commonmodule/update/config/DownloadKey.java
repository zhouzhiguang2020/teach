package com.futureeducation.commonmodule.update.config;

import android.content.Context;

/**
 * Created by hugeterry(http://hugeterry.cn)
 */
public class DownloadKey {
    @Deprecated
    public static Context FROMACTIVITY = null;
    public static Boolean ISManual = false;
    public static Boolean LoadManual = false;
    public static int TOShowDownloadView = 0;
    //public static final String saveFileName = Environment.getExternalStorageDirectory() + "/UpdateFun/gwy.apk";
    public static String saveFileName = "FEDU_student.apk";
    public static String apkUrl = "";
    public static String changeLog = "";//跟新日志
    public static String version;
    public static boolean interceptFlag = false;//是否可以中断
    public static boolean IsCompel = false;//是否强制跟新
    public static boolean IsNeedUpdate;//是否需要更新
}
