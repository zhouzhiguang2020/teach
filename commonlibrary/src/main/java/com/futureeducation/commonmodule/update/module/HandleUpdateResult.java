package com.futureeducation.commonmodule.update.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.update.config.DownloadKey;
import com.futureeducation.commonmodule.update.utils.GetAppInfo;
import com.futureeducation.commonmodule.update.view.UpdateDialog;

import java.lang.ref.WeakReference;


/**
 * 工务园
 */
public class HandleUpdateResult implements Runnable {

    private String version = "";
    private Up_handler up_handler;
    private Context context;

    public HandleUpdateResult(Context context) {
        this.context = context;
        up_handler = new Up_handler(context);
        this.version = GetAppInfo.getAppVersionName(context);
    }

    private static class Up_handler extends Handler {
        WeakReference<Context> mActivityReference;

        public Up_handler(Context context) {

            mActivityReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            final Context context = mActivityReference.get();
            if (context != null) {
                switch (msg.arg1) {
                    case 1:
                        LogUtils.e("检查版本：showNoticeDialog");
                        showNoticeDialog(context);
                        break;
                    case 2:
                        if (DownloadKey.ISManual) {
                            DownloadKey.LoadManual = false;
                            LogUtils.e("检查版本：网络不畅通");
                            //Toast.makeText(context, "网络不畅通", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 3:
                        if (DownloadKey.ISManual) {
                            DownloadKey.LoadManual = false;

                            LogUtils.e("检查版本：版本已是最新");
                            //Toast.makeText(context, "版本已是最新", Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void run() {
        Update update = new Update(context);
        update.start();

        try {
            update.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        update.setOnUpdateInfoListener(new Update.OnUpdateInfoListener() {
            @Override
            public void OnRequestFinish() {
                Message msg = new Message();
                if (DownloadKey.IsNeedUpdate) {
                    LogUtils.e("UpdateFun TAG" + "需更新版本");
                    msg.arg1 = 1;
                    up_handler.sendMessageDelayed(msg, 1000);
                } else {
                    LogUtils.e("UpdateFun TAG" + "版本已是最新");
                    msg.arg1 = 3;
                    up_handler.sendMessage(msg);
                }
            }

            @Override
            public void OnRequestdefeated() {

                CancelThread();
            }
        });

    }

    public void CancelThread() {
        if (up_handler != null) {
            up_handler.removeCallbacksAndMessages(null);

            up_handler = null;
        }
        LogUtils.e("取消了:--");
    }

    public static void showNoticeDialog(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setClass(context, UpdateDialog.class);
        ((Activity) context).startActivityForResult(intent, 100);
    }

}
