package com.futureeducation.commonmodule.update.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.futureeducation.commonmodule.update.config.UpdateKey;

import java.io.File;

/**
 * 描述:     TODO 通知工具类
 *
 * @author zhou
 */

public final class NotificationUtil {


    /**
     * 显示下载完成的通知,点击进行安装
     *
     * @param context     上下文
     * @param icon        图标
     * @param title       标题
     * @param content     内容
     * @param authorities Android N 授权
     * @param apk         安装包
     */
    public static void showDoneNotification(Context context, int icon, String title, String content,
                                            String authorities, File apk) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //不知道为什么需要先取消之前的进度通知，才能显示完成的通知。
        manager.cancel(UpdateKey.NOTIFICATIONID);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authorities, apk);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apk);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                .setContentIntent(pi);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(UpdateKey.NOTIFICATIONID, notification);
    }

    /**
     * 构建一个消息
     *
     * @param context 上下文
     * @param icon    图标id
     * @param title   标题
     * @param content 内容
     */
    public static NotificationCompat.Builder builderNotification(Context context, int icon, String title, String content) {
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = String.valueOf(UpdateKey.NOTIFICATIONID);
        }
        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .setContentText(content)
                //不能删除
                .setAutoCancel(false)
                //正在交互（如播放音乐）
                .setOngoing(true);
    }


}
