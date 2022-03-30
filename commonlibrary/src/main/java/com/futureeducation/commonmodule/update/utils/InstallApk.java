package com.futureeducation.commonmodule.update.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.futureeducation.commonmodule.R;

import java.io.File;
import java.io.IOException;


/**
 *
 */
public class InstallApk {
    private static boolean showNotification = true;

    public static void startInstall(Context context, File apkfile) {
        if (!apkfile.exists()) {
            return;
        }
        //注意权限问题有可能文件没有执行权限
        String[] command = {"chmod", "777", apkfile.getPath()};
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pagename = GetAppInfo.getAppPackageName(context);
        String authorities = null;
        if (TextUtils.equals(pagename, "com.future_education.teacher")) {
            authorities = context.getString(R.string.updatefun_provider_file_authorities);
        } else {
            authorities = context.getString(R.string.updatefun_assistant_provider_file_authorities);
        }
        //如果是android Q（api=29）及其以上版本showNotification=false也会发送一个下载完成通知
        if (showNotification || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String downloadCompleted = context.getResources().getString(R.string.download_completes);
            String clickHint = context.getResources().getString(R.string.click_hint);
            int smallIcon = R.mipmap.ic_launcher;

            NotificationUtil.showDoneNotification(context, smallIcon, downloadCompleted,
                    clickHint, authorities, apkfile);
        }

        ApkUtil.installApk(context, authorities, apkfile);

    }


}
