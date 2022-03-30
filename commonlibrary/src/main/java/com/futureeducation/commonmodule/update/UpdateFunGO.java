package com.futureeducation.commonmodule.update;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.update.config.DownloadKey;
import com.futureeducation.commonmodule.update.config.UpdateKey;
import com.futureeducation.commonmodule.update.module.Download;
import com.futureeducation.commonmodule.update.module.HandleUpdateResult;
import com.futureeducation.commonmodule.update.utils.GetAppInfo;
import com.futureeducation.commonmodule.update.view.DownLoadDialog;


/**
 * 复得科技
 */
public class UpdateFunGO {

    private static Thread download;
    private static Thread thread_update;
    private static HandleUpdateResult handleUpdateResult;
    private static volatile UpdateFunGO sInst = null;

    public static void manualStart(Context context) {
        DownloadKey.ISManual = true;
        if (!DownloadKey.LoadManual) {
            DownloadKey.LoadManual = true;
            new UpdateFunGO(context);
        } else {
            Toast.makeText(context, "正在更新中,请稍等", Toast.LENGTH_LONG).show();
        }
    }

    public static UpdateFunGO init(Context context) {
        UpdateFunGO inst = sInst;
        if (inst == null) {
            synchronized (UpdateFunGO.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new UpdateFunGO(context);
                    sInst = inst;
                }
            }
        }
        LogUtils.e("开始检查跟新了--");
        return inst;
    }

    private UpdateFunGO(Context context) {
        if (DownloadKey.TOShowDownloadView != 2) {
            handleUpdateResult = new HandleUpdateResult(context);
            thread_update = new Thread(handleUpdateResult);
            thread_update.start();
        }
    }

    public static void showDownloadView(Context context) {
        DownloadKey.saveFileName =
                GetAppInfo.getAppPackageName(context) + ".apk";
        LogUtils.e("通知-- showDownloadView-");
        LogUtils.e("通知-- (UpdateKey.DialogOrNotification-" + UpdateKey.DialogOrNotification);

        if (UpdateKey.DialogOrNotification == 0) {
            //默认后台下载模式
            download = new Download(context);
            download.start();

        } else if (UpdateKey.DialogOrNotification == 1) {
            //对话框模式下载
            Intent intent = new Intent();
            intent.setClass(context, DownLoadDialog.class);
            LogUtils.e("创建对话框栏跟新了--");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ((Activity) context).startActivityForResult(intent, 0);
        } else if (UpdateKey.DialogOrNotification == 2) {
            checkoutNotificationPermissions(context);
            //通知栏模式
            NotificationCompat.Builder builder = notificationInit(context);
            download = new Download(context, builder);
            download.start();
            LogUtils.e("通知栏载跟新了---");
        } else if (UpdateKey.DialogOrNotification == 3) {

            //紧急情况是跳转到浏览器下载
            openBrowser(context, DownloadKey.apkUrl);
        }


    }

    private static void checkoutNotificationPermissions(Context context) {


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager == null) {
                return;
            }
            NotificationChannel channel = new NotificationChannel(GetAppInfo.getAppPackageName(context),
                    GetAppInfo.getAppPackageName(context),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
//        Notification notification = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id_default))
//                .setContentTitle("通知标题")
//                .setContentInfo("通知内容")
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .build();
//        NotificationManagerCompat.from(context).notify(100, notification);
//        LogUtils.e("显示通知了吗--");

//        //检查通知栏权限
//        AndPermission.with(context)
//                .notification()
//                .permission()
//                .rationale(new NotifyRationale())
//                .onDenied(new Action<Void>() {
//                    @Override
//                    public void onAction(Void data) {
//                        LogUtils.e("拒绝了通知栏权限");
//                        NotificationUtils.openNotificationSettings(context);
//                    }
//                }).onGranted(new Action<Void>() {
//
//
//            @Override
//            public void onAction(Void data) {
//                LogUtils.e("同意通知栏权限");
//                //NotificationUtils.openNotificationSettings(context);
//
//            }
//        }).start();
    }


    public static AppCompatActivity getActivityByContext(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    @SuppressLint("WrongConstant")
    private static NotificationCompat.Builder notificationInit(Context context) {
        Intent intent = new Intent(context, context.getClass());
        PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, GetAppInfo.getAppPackageName(context));
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        //builder.setAutoCancel(true);    //点击自动消息
        builder.setSmallIcon(android.R.drawable.stat_sys_download)
                .setLargeIcon(GetAppInfo.drawableToBitmap(GetAppInfo.getAppIcons(context)))
                .setTicker("开始下载")
                .setContentInfo("版本升级")
                .setContentTitle(GetAppInfo.getAppName(context) + "版本升级")
                .setContentText("正在更新")
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentIntent(pIntent)
//                .setOngoing(true)
//                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        return builder;
    }

    public static void onResume(Context context) {
        if (DownloadKey.TOShowDownloadView == 2) {
            showDownloadView(context);

        } else {
            if (sInst != null) sInst = null;
        }
    }

    public static void onStop(Context context) {
        if (DownloadKey.TOShowDownloadView == 2 && UpdateKey.DialogOrNotification == 2) {
            download.interrupt();
        }
        if (thread_update != null) {
            thread_update.interrupt();

        }
        if (DownloadKey.ISManual) {
            DownloadKey.ISManual = false;
        }
        if (DownloadKey.LoadManual) {
            DownloadKey.LoadManual = false;
        }
        if (handleUpdateResult != null) {
            handleUpdateResult.CancelThread();
        }
    }

    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    private static void openBrowser(Context context, String url) {
        Intent i = new Intent();
        i.setAction("android.intent.action.VIEW");
        Uri u = Uri.parse(url);
        i.setDataAndType(u, "text/html");
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (i.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(i);
        }
        LogUtils.e("跳转到浏览器下载" + url);
    }
}
